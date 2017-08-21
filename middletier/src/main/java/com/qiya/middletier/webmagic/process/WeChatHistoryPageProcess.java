package com.qiya.middletier.webmagic.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;
import com.qiya.middletier.webmagic.pipe.RinseWeChatPipeline;

import us.codecraft.webmagic.Page;

/**
 * Created by qiyamac on 2017/3/24.
 */
@Service
@Scope("prototype")
public class WeChatHistoryPageProcess extends AbstractCommPageProcess {

	private static Logger log = LoggerFactory.getLogger(WeChatHistoryPageProcess.class);

	public WeChatHistoryPageProcess() {
	}

	@Override
	public void process(Page page) {
		if (page.getUrl().regex(getConfig().getRule().getListregex()).match()) {
			log.info("微信文章列表数据爬取开始：url-" + page.getResultItems().getRequest().getUrl());

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				Map map = objectMapper.readValue(page.getRawText(), Map.class);
				if ("ok".equals(map.get("errmsg").toString())) {
					String msg = String.valueOf(map.get("general_msg_list"));
					Map msgmap = objectMapper.readValue(msg, Map.class);
					List<Map> mapList = (List<Map>) msgmap.get("list");
					if (mapList != null && mapList.size() > 0) {
						if ("1".equals(map.get("can_msg_continue").toString())) {
							Map amap = mapList.get(mapList.size() - 1);
							Map commmsginfo = (Map) amap.get("comm_msg_info");
							String msgid = commmsginfo.get("id").toString();
							String url = getConfig().getWechat().getUrl();
							List listurl = new ArrayList();
							listurl.add(url.replace("[MESSAGEID]", msgid));
							page.addTargetRequests(listurl, 9999);
						}
						for (Map msgMap : mapList) {
							if (msgMap.containsKey("app_msg_ext_info")) {
								Map urlmap = (Map) msgMap.get("app_msg_ext_info");
								if (urlmap.get("content_url") != null && urlmap.get("content_url").toString().length() > 0) {
									if (urlmap.get("cover") != null && urlmap.get("cover").toString().length() > 0) {
										page.addTargetRequest(urlmap.get("content_url").toString() + "图片" + urlmap.get("cover").toString());
									} else {
										page.addTargetRequest(urlmap.get("content_url").toString());
									}

								}
								int is_multi = Integer.valueOf(urlmap.get("is_multi").toString());
								if (1 == is_multi) {
									List<Map> multimapList = (List<Map>) urlmap.get("multi_app_msg_item_list");
									for (Map itemMap : multimapList) {
										if (itemMap.get("content_url") != null && itemMap.get("content_url").toString().length() > 0) {
											if (itemMap.get("cover") != null && itemMap.get("cover").toString().length() > 0) {
												page.addTargetRequest(itemMap.get("content_url").toString().replaceAll("&amp;", "&") + "图片" + itemMap.get("cover").toString());
											} else {
												page.addTargetRequest(itemMap.get("content_url").toString().replaceAll("&amp;", "&"));
											}

										}
									}
								}

							}

						}

					}

				}
			} catch (IOException e) {
				e.printStackTrace();

			}

			page.getResultItems().setSkip(true);
		} else if(page.getUrl().regex(getConfig().getRule().getDetailregex()).match()){
			log.info("微信文章数据数据爬取开始：url-" + page.getResultItems().getRequest().getUrl());
			setPutFile(page, getConfig().getRule());

		}else {
			page.getResultItems().setSkip(true);
		}
		setIsCircle(page);

	}

	@Override
	public void init(WebmagicConfig config) {
		this.setConfig(config);
	}


}
