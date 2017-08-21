package com.qiya.middletier.webmagic.pipe;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qiya.framework.def.StatusEnum;
import com.qiya.middletier.bizenum.SiteType;
import com.qiya.middletier.model.Article;
import com.qiya.middletier.model.ArticleDetail;
import com.qiya.middletier.model.Site;
import com.qiya.middletier.service.*;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by qiyalm on 17/3/24.
 */
@Service
@Scope("prototype")
public class WeChatMsgMySqlArticlePipeline implements Pipeline {

	private static Logger log = LoggerFactory.getLogger(WeChatMsgMySqlArticlePipeline.class);

	@Autowired
	private ArticleService articleService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private ArticleDetailService articleDetailService;

	@Autowired
	private TaskService taskService;

	//@Autowired
	//private RecommendListService recommendListService;

	@Override
	public void process(ResultItems resultItems, Task task) {
		if (resultItems.isSkip() == false) {
			try {

				Map<String, Object> data = resultItems.getAll();
				if (data.size() == 0) {
					resultItems.setSkip(true);
					return;
				}

				/*
				 * 添加文章信息
				 */
				Article article = new Article();
				BeanUtils.populate(article, resultItems.getAll());
				if (data.containsKey("weicahtNo")) {
					String weicahtNo = data.get("weicahtNo").toString();
					String weicahtName = data.get("weicahtName").toString();
					String weicahtNo2 = data.get("weicahtNo2").toString();
					if (StringUtils.isEmpty(weicahtNo)) {
						weicahtNo = weicahtNo2;
					}
					Site site = siteService.getSiteBYName(weicahtNo);

					if (site == null) {
						site = siteService.create(new Site(weicahtNo, weicahtName, "mp.weixin.qq.com", SiteType.WECHAT.getId()));
					}
					article.setSiteId(site.getId());
				}

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
					log.info("微信转发文章保存数据库:" + article.getLinkUrl() + "!");
					article = articleService.create(article);
				//	String beforeContent = data.get("beforeContent") == null ? "" : data.get("beforeContent").toString();
				//	String content = data.get("content") == null ? beforeContent : data.get("content").toString();
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
					//recommendListService.recommend(article.getId());
				} else {
					log.info("推荐文章进入列表:" + article.getLinkUrl() + "!");
					Article a = articleService.getArticlebyTiteSiteAuthor(article.getSiteId(), article.getTitle(), article.getAuthor());
					if (a != null) {
						//recommendListService.recommend(a.getId());
					}
					resultItems.setSkip(true);

				}
			} catch (Exception ex) {
				log.error("保存到mysql出错:" + ex.getLocalizedMessage());
				ex.printStackTrace();
			}
		}
	}
}
