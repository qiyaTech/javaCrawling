package com.qiya.middletier.webmagic.pipe;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by qiyalm on 17/3/24.
 */
@Service
@Scope("prototype")
public class RinseWeChatPipeline implements Pipeline {

	private static Logger log = LoggerFactory.getLogger(RinseWeChatPipeline.class);

	@Value("${weichatImg:}")
	private String weichatImg;

	@Override
	public void process(ResultItems resultItems, Task task) {
		if(resultItems.isSkip()==false) {
			if (resultItems.get("pic") != null) {
				String picurl = resultItems.get("pic").toString();
				picurl = weichatImg + picurl;
				resultItems.put("pic", picurl);
			}
		}

	}
}
