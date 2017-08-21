package com.qiya.middletier.webmagic.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.qiya.middletier.webmagic.comm.configmodel.RinseRule;
import us.codecraft.webmagic.utils.UrlUtils;

/**
 * Created by qiyamac on 2017/3/31.
 */
public class RinseHtmlUtil {

	public static Document rinseHtml(Document document, List<RinseRule> rinseRules) {
		for (RinseRule rinseRule : rinseRules) {
			document = rinseDocument(document, rinseRule);
		}

		return document;
	}

	public static String rinseHtml(String html, List<RinseRule> rinseRules) {
		Document document = Jsoup.parse(html);

		for (RinseRule rinseRule : rinseRules) {
			document = rinseDocument(document, rinseRule);
		}

		return document.body().children().outerHtml();
	}

	public static String  absImgUrl(String html, String pageurl) {
		Document document = Jsoup.parse(html);
		Elements elements = document.select("img");

		for (Element element : elements) {
			String attrVlue = element.attr("src");
			attrVlue= UrlUtils.canonicalizeUrl(attrVlue,pageurl);
			element.attr("src", attrVlue);
		}
		return document.body().children().outerHtml();
	}
	public static Document rinseDocument(Document document, RinseRule rinseRule) {
		switch (rinseRule.getAction())

		{
		case "replace":

			return replace(document, rinseRule);

		case "fullreplace":

			return fullReplace(document, rinseRule);

		case "add":

			return add(document, rinseRule);

		case "delete":

			return delete(document, rinseRule);

		case "copy":

			return copy(document, rinseRule);

		default:
			return document;

		}

	}

	public static Document replace(Document document, RinseRule rinseRule) {
		Elements elements = document.select(rinseRule.getCssquery());
		switch (rinseRule.getType()) {

		case "attr":

			for (Element element : elements) {
				String attrVlue = element.attr(rinseRule.getName());
				attrVlue = attrVlue.replaceAll(rinseRule.getSource(), rinseRule.getTarget());
				element.attr(rinseRule.getName(), attrVlue);
			}
			break;

		case "text":

			for (Element element : elements) {
				String text = element.text();
				text = text.replaceAll(rinseRule.getSource(), rinseRule.getTarget());
				element.text(text);
			}

			break;
		case "node":

			for (Element element : elements) {

				String nodestr = element.outerHtml().replaceAll(rinseRule.getSource(), rinseRule.getTarget());
				Document node = Jsoup.parse(nodestr);
				element.replaceWith(node.body().children().first());

			}

			break;
		case "style":
			for (Element element : elements) {
				replaceStyle(element, rinseRule);
			}

			break;

		default:
			break;

		}
		return document;
	}

	public static Document fullReplace(Document document, RinseRule rinseRule) {
		String html = document.body().children().outerHtml();
		html = html.replaceAll(rinseRule.getSource(), rinseRule.getTarget());
		return Jsoup.parse(html);
	}

	public static Document add(Document document, RinseRule rinseRule) {
		Elements elements = document.select(rinseRule.getCssquery());
		switch (rinseRule.getType())

		{

		case "attr":
			elements.attr(rinseRule.getName(), rinseRule.getValue());
			break;

		case "text":
			for (Element element : elements) {
				element.appendText(element.text() + rinseRule.getValue());

			}

			break;
		case "node":
			Node node = Jsoup.parse(rinseRule.getValue());
			for (Element element : elements) {
				element.appendChild(node);

			}
			break;
		case "style":
			for (Element element : elements) {
				addStyle(element, rinseRule);
			}

			break;

		default:
			break;

		}
		return document;

	}

	public static Document delete(Document document, RinseRule rinseRule) {
		Elements elements = document.select(rinseRule.getCssquery());
		switch (rinseRule.getType()) {

		case "attr":
			elements.removeAttr(rinseRule.getName());
			break;

		case "text":

			for (Element element : elements) {
				element.text("");
			}

			break;
		case "node":
			elements.remove();
			break;

		case "style":
			for (Element element : elements) {
				deleteStyle(element, rinseRule);
			}

			break;

		default:
			break;

		}
		return document;
	}

	public static void deleteStyle(Element element, RinseRule rinseRule) {
		String attrVlue = element.attr("style");
		String[] keys = rinseRule.getName().split(":");
		String[] csss = attrVlue.split(";");
		Map<String, String> cssMap = new HashMap<>();
		for (String css : csss) {
			if (StringUtils.isNotEmpty(css)) {
				String[] cssValue = css.split(":");
				if (cssValue.length >= 2) {
					cssMap.put(cssValue[0].trim(), cssValue[1]);
				}
			}
		}
		for (String key : keys) {
			if (StringUtils.isNotEmpty(key) && cssMap.containsKey(key)) {
				cssMap.remove(key);
			}
		}
		StringBuffer sb = new StringBuffer();
		Set<String> keysets = cssMap.keySet();
		for (String key : keysets) {
			String stylevalue = cssMap.get(key);
			sb.append(key).append(":").append(stylevalue).append(";");
		}
		if (sb.length() > 0) {
			element.attr("style", sb.toString());
		} else {
			element.removeAttr("style");
		}

	}

	public static void addStyle(Element element, RinseRule rinseRule) {
		String attrVlue = element.attr("style");
		String[] csss = attrVlue.split(";");
		Map<String, String> cssMap = new HashMap<>();
		for (String css : csss) {
			if (StringUtils.isNotEmpty(css)) {
				String[] cssValue = css.split(":");
				if (cssValue.length >= 2) {
					cssMap.put(cssValue[0].trim(), cssValue[1]);
				}
			}
		}
		cssMap.put(rinseRule.getName(), rinseRule.getValue());
		StringBuffer sb = new StringBuffer();
		Set<String> keysets = cssMap.keySet();
		for (String key : keysets) {
			String stylevalue = cssMap.get(key);
			sb.append(key).append(":").append(stylevalue).append(";");
		}
		if (sb.length() > 0) {
			element.attr("style", sb.toString());
		} else {
			element.removeAttr("style");
		}

	}

	public static void replaceStyle(Element element, RinseRule rinseRule) {
		String attrVlue = element.attr("style");

		String[] csss = attrVlue.split(";");
		Map<String, String> cssMap = new HashMap<>();
		for (String css : csss) {
			if (StringUtils.isNotEmpty(css)) {
				String[] cssValue = css.split(":");
				if (cssValue.length >= 2) {
					if (cssValue[0].trim().equals(rinseRule.getName())) {
						cssMap.put(cssValue[0].trim(), cssValue[1].replaceAll(rinseRule.getSource(), rinseRule.getTarget()));
					} else {
						cssMap.put(cssValue[0].trim(), cssValue[1]);
					}

				}
			}
		}

		StringBuffer sb = new StringBuffer();
		Set<String> keysets = cssMap.keySet();
		for (String key : keysets) {
			String stylevalue = cssMap.get(key);
			sb.append(key).append(": ").append(stylevalue).append("; ");
		}
		if (sb.length() > 0) {
			element.attr("style", sb.toString());
		} else {
			element.removeAttr("style");
		}

	}

	public static Document copy(Document document, RinseRule rinseRule) {

		Elements elements = document.select(rinseRule.getCssquery());
		switch (rinseRule.getType()) {

		case "attr":
			for (Element element : elements) {
				String attrVlue = element.attr(rinseRule.getSource());
				element.attr(rinseRule.getTarget(), attrVlue);
			}

			break;

		default:
			break;

		}
		return document;
	}

}
