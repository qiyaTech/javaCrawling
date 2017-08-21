package com.qiya.middletier.webmagic.pipe;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RinsePipeline implements Pipeline {

	private static Logger log = LoggerFactory.getLogger(RinsePipeline.class);

	@Override
	public void process(ResultItems resultItems, Task task) {
		if(resultItems.isSkip()==false) {
			log.info("基本数据清洗开始：url-" + resultItems.getRequest().getUrl());
			if (resultItems.get("beforeContent") == null || resultItems.get("beforeContent").toString().length() == 0) {
				resultItems.setSkip(true);
				return;
			}

			String html = resultItems.get("beforeContent").toString();

			Document document = Jsoup.parse(html);
			Elements pngs = document.select("img");
			for (Element e : pngs) {
				e.attr("width", "100%");
				e.attr("height", "auto");
				String style = e.attr("style");
				String re = "width: auto";
				style = style.replaceAll(re, "width: 100%");
				re = "width: \\d+px";
				style = style.replaceAll(re, "width: 100%");
				re = "width: \\d+.\\d+px";
				style = style.replaceAll(re, "width: 100%");
				re = "height: \\d+px";
				style = style.replaceAll(re, "height: auto");
				re = "height: \\d+.\\d+px";
				style = style.replaceAll(re, "height: auto");
				e.attr("style", style);

			}
			log.info("基本数据清洗结束：url-" + resultItems.getRequest().getUrl());
			html = document.body().children().outerHtml();

			html = html.replaceAll("h1", "h5");
			html = html.replaceAll("h2", "h5");
			html = html.replaceAll("h3", "h5");
			html = html.replaceAll("h4", "h5");

			resultItems.put("content", html);
		}
	}
}
