package com.qiya.middletier.webmagic.pipe;

import java.util.List;

import com.qiya.framework.coreservice.RedisService;
import com.qiya.framework.coreservice.SysService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.qiya.middletier.webmagic.comm.configmodel.RinseRule;
import com.qiya.middletier.webmagic.util.RinseHtmlUtil;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.UrlUtils;

/**
 * Created by qiyalm on 17/3/24.
 */
@Service
@Scope("prototype")
public class RinseCommPipeline implements Pipeline {

	private static Logger log = LoggerFactory.getLogger(RinseCommPipeline.class);




	@Override
	public void process(ResultItems resultItems, Task task) {
		if(resultItems.isSkip()==false) {

			log.info("数据根据模版规则清洗开始：url-" + resultItems.getRequest().getUrl());
			String html = resultItems.get("beforeContent").toString();
			//通用模版清洗
			if (resultItems.get("commRinseRules") != null) {
				List<RinseRule> rinseRules = resultItems.get("commRinseRules");
				html = RinseHtmlUtil.rinseHtml(html, rinseRules);
			}
			if (resultItems.get("rinseRules") != null) {
				List<RinseRule> rinseRules = resultItems.get("rinseRules");
				html = RinseHtmlUtil.rinseHtml(html, rinseRules);

			}
			//设置图片绝对地址
			html=RinseHtmlUtil.absImgUrl(html,resultItems.getRequest().getUrl());
			resultItems.put("content", html);
		}
	}





}
