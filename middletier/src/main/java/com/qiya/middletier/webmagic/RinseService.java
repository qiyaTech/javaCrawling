package com.qiya.middletier.webmagic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.qiya.framework.middletier.model.Config;
import com.qiya.framework.middletier.service.BizConfigService;
import com.qiya.framework.model.SearchCondition;
import com.qiya.middletier.bizenum.BizConfigTypeEnum;
import com.qiya.middletier.model.Article;
import com.qiya.middletier.model.ArticleDetail;
import com.qiya.middletier.model.ArticleResult;
import com.qiya.middletier.model.Task;
import com.qiya.middletier.service.ArticleDetailService;
import com.qiya.middletier.service.ArticleService;
import com.qiya.middletier.service.TaskService;
import com.qiya.middletier.webmagic.comm.configmodel.RinseRule;
import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;
import com.qiya.middletier.webmagic.util.RinseHtmlUtil;

/**
 * Created by qiyamac on 2017/5/17.
 */
@Service
public class RinseService {

	private static Logger log = LoggerFactory.getLogger(RinseService.class);

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleDetailService articleDetailService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private BizConfigService configService;

	@Async
	public void rinseByartliIdtList(List<String> artliIdtList, String taskId) {
		List<RinseRule> rinseRules = new ArrayList<>();
		List<RinseRule> commrinseRules = new ArrayList<>();
		commrinseRules = getCommmRinse();
		rinseRules = getTaskRinse(taskId);
		for (String id : artliIdtList) {
			Article article = articleService.read(id);
			ArticleDetail articleDetail = articleDetailService.read(id);
			if (articleDetail != null) {
				rinseContent(articleDetail, article, commrinseRules, rinseRules);
			}
		}

	}


	@Async
	public void rinseBySearchCondition(SearchCondition sc, String taskId) {
		List<RinseRule> rinseRules = new ArrayList<>();
		List<RinseRule> commrinseRules = new ArrayList<>();
		commrinseRules = getCommmRinse();
		rinseRules = getTaskRinse(taskId);
		Page<ArticleResult> articlePage = articleService.searchBySite(sc);
		if (articlePage != null && articlePage.getContent() != null) {
			List<ArticleResult> list = articlePage.getContent();
			for (ArticleResult a : list) {
				ArticleDetail articleDetail = articleDetailService.read(a.getId().toString());
				if (articleDetail != null) {
					rinseContent(articleDetail, a, commrinseRules, rinseRules);
				}
			}
		}
	}

	public List<RinseRule> getCommmRinse() {
		List<RinseRule> rinseRules = new ArrayList<>();
		try {
			// 设置通用规则
			List<Config> configs = configService.getByCode(BizConfigTypeEnum.COMMRISE.getCode());
			if (configs != null && configs.size() > 0) {
				String json = configs.get(0).getValue();
				ObjectMapper objectMapper = new ObjectMapper();
				CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, RinseRule.class);
				rinseRules = objectMapper.readValue(json, listType);

			}

		} catch (Exception e) {
			log.error("获取通用模版清洗规则错误：", e);
		}
		return rinseRules;
	}

	public List<RinseRule> getTaskRinse(String taskId) {
		List<RinseRule> rinseRules = new ArrayList<>();
		Task task = taskService.read(taskId);
		String json = task.getTaskRuleJson();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			WebmagicConfig webmagicConfig = objectMapper.readValue(json, WebmagicConfig.class);
			rinseRules = webmagicConfig.getRinseRules();
		} catch (Exception e) {
			log.error("获取清洗规则错误：", e);

		}

		return rinseRules;
	}

	public void rinseContent(ArticleDetail articleDetail, Article article, List<RinseRule> commrinseRules, List<RinseRule> rinseRules) {
		log.info("手动清洗文章清洗开始：title -" + article.getTitle());
		String html = "";
		if (StringUtils.isNotEmpty(articleDetail.getBeforeContent())) {
			html = articleDetail.getBeforeContent();
			html = RinseHtmlUtil.rinseHtml(html, commrinseRules);
			html = RinseHtmlUtil.rinseHtml(html, rinseRules);
		}
		// 设置图片绝对地址
		html = RinseHtmlUtil.absImgUrl(html, article.getLinkUrl());
		articleDetail.setContent(html);
		articleDetailService.update(articleDetail);
		log.info("手动清洗文章清洗结束：title -" + article.getTitle());
	}

}
