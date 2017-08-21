package com.qiya.middletier.webmagic.pipe;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qiya.framework.baselib.util.generate.GenerateUtils;
import com.qiya.framework.coreservice.RedisService;
import com.qiya.framework.coreservice.SysService;
import com.qiya.framework.coreservice.qiniu.QiniuService;
import com.qiya.framework.def.StatusEnum;
import com.qiya.middletier.bizenum.SiteType;
import com.qiya.middletier.bizenum.TaskPropressEnum;
import com.qiya.middletier.bizenum.TaskRunStateEnum;
import com.qiya.middletier.model.Article;
import com.qiya.middletier.model.ArticleDetail;
import com.qiya.middletier.model.Site;
import com.qiya.middletier.service.ArticleDetailService;
import com.qiya.middletier.service.ArticleService;
import com.qiya.middletier.service.SiteService;
import com.qiya.middletier.service.TaskService;
import com.qiya.middletier.webmagic.monitor.MySpiderMonitor;
import com.qiya.middletier.webmagic.monitor.MySpiderStatus;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by qiyalm on 17/3/24.
 */
@Service
@Scope("prototype")
public class MySqlArticlePipeline implements Pipeline {

	private static Logger log = LoggerFactory.getLogger(MySqlArticlePipeline.class);

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleDetailService articleDetailService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private SiteService siteService;

	@Autowired
	QiniuService qiniuService;

	@Autowired
	SysService sysService;

	@Value("${system.name:none}")
	private String systemName;

	@Value("${qiniu.http.context:}")
	private String qinniuHttpContext;

	final private String PREFIX = "ARTIC_LIST_IMAGE";
	final private String QINNIU_PREFIX = "daydayup/";
	final private String SPLIT = "|";

	@Override
	public void process(ResultItems resultItems, Task task) {
		if (resultItems.isSkip() == false) {
			try {

				Map<String, Object> data = resultItems.getAll();
				if (data.size() == 0) {
					resultItems.setSkip(true);
					return;
				}

				// 判断时间是否在开始时间和结束时间内
				if (data.get("taskStatus") != null) {
					int taskStatus = Integer.parseInt(data.get("taskStatus").toString());

					if (taskStatus == TaskPropressEnum.TASKSTOP.getValue()) {
						// 结束爬取任务
						log.info("爬取历史文章日期已超出开始时间范围,爬取任务结束.");

						MySpiderMonitor spiderMonitor = MySpiderMonitor.instance();
						Map<String, MySpiderStatus> spiderStatuses = spiderMonitor.getSpiderStatuses();
						MySpiderStatus spiderStatus = spiderStatuses.get(task.getUUID());

						spiderStatus.stop();
						spiderStatus.getSpider().close();

						// 修改任务状态
						com.qiya.middletier.model.Task task1 = taskService.getByUUID(task.getUUID());
						task1.setEndTime(new Date());
						task1.setRunState(TaskRunStateEnum.TASK_STOP.getCode());
						taskService.update(task1);
						return;

					} else if (taskStatus == TaskPropressEnum.CONTINUE.getValue()) {
						log.info("爬取历史文章日期未达到指定时间范围,不保存数据库,爬取任务继续.");
						// 跳过 继续爬
						resultItems.setSkip(true);
						return;
					}
					log.info("爬取历史文章日期是指定时间范围,保存数据库,爬取任务继续.");
				}

				/*
				 * 添加文章信息
				 */
				Article article = new Article();
				BeanUtils.populate(article, resultItems.getAll());

				article.setCreateTime(new Date());
				article.setStatus(StatusEnum.VALID.getValue());
				article.setLinkUrl(resultItems.getRequest().getUrl());

				if (StringUtils.isEmpty(article.getTitle())) {
					resultItems.setSkip(true);
					return;
					//article.setTitle("");
				}
				String beforeContent = data.get("beforeContent") == null ? "" : data.get("beforeContent").toString();
				String content = data.get("content") == null ? beforeContent : data.get("content").toString();

				if (StringUtils.isEmpty(beforeContent)) {
					resultItems.setSkip(true);
					return;
				}
				if (StringUtils.isEmpty(content)) {
					resultItems.setSkip(true);
					return;
				}
				if (StringUtils.isEmpty(article.getAuthor()))
					article.setAuthor("");
				if (StringUtils.isEmpty(article.getPublicTime()))
					article.setPublicTime(new Date());
				if (StringUtils.isEmpty(article.getIntroduce()))
					article.setIntroduce("");
				if (StringUtils.isEmpty(article.getPic()))
					article.setPic("");
				if (StringUtils.isEmpty(article.getGoodCount()))
					article.setGoodCount(0);
				if (StringUtils.isEmpty(article.getReadCount()))
					article.setReadCount(0);

				if (articleService.isExists(article.getLinkUrl()) && articleService.isExistTiteSiteTime(article.getSiteId(), article.getTitle(), article.getPublicTime())) {

					//增加图片,并且上传到七牛
					Site site = siteService.read(String.valueOf(article.getSiteId()));
					if(site.getType().compareTo(SiteType.WECHAT.id)==0)
					{
						String url = article.getPic();
						if(!StringUtils.isEmpty(url))
						{
							byte[] imageByte = articleService.getWechatImage(url);
							String imageName = this.QINNIU_PREFIX+sysService.getActiveProfile()+"/"+ GenerateUtils.getUUID();
							String key = qiniuService.uploadFileKey(imageByte,imageName);
							log.info("微信存储七牛地址:{}",key);
							article.setPic(qinniuHttpContext+key);
						}
					}
					else if(site.getType().compareTo(SiteType.WEB.id)==0)
					{
						String url = article.getPic();
						if(!StringUtils.isEmpty(url))
						{
							String imageName = this.QINNIU_PREFIX+sysService.getActiveProfile()+"/"+ GenerateUtils.getUUID();
							String key = qiniuService.uploadFileByUrl(url,imageName);
							log.info("网站存储七牛地址:{}",key);
							article.setPic(qinniuHttpContext+key);
						}

					}

					article = articleService.create(article);
					log.info("爬取任务文章保存数据库:"+article.getLinkUrl()+"!");
					//String beforeContent = data.get("beforeContent") == null ? "" : data.get("beforeContent").toString();
					//String content = data.get("content") == null ? beforeContent : data.get("content").toString();
					if (beforeContent == "") {
						beforeContent = content;
					}

					// 添加文章详细信息
					ArticleDetail articleDetail = new ArticleDetail();
					articleDetail.setId(article.getId());
					articleDetail.setBeforeContent(beforeContent);
					articleDetail.setContent(content);
					articleDetail.setCreateTime(article.getCreateTime());
					articleDetail.setStatus(article.getStatus());

					articleDetailService.create(articleDetail);
				} else {
					resultItems.setSkip(true);
				}
			} catch (Exception ex) {
				log.error("保存到mysql出错:" + ex.getLocalizedMessage());
				ex.printStackTrace();
			}
		}
	}
}
