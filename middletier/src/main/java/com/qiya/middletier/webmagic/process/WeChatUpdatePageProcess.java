package com.qiya.middletier.webmagic.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiya.middletier.bizenum.SpiderExceptionEnum;
import com.qiya.middletier.webmagic.comm.configmodel.SpiderExceptionMsg;
import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;

import us.codecraft.webmagic.Page;

/**
 * Created by qiyamac on 2017/3/24.
 */
@Service
@Scope("prototype")
public class WeChatUpdatePageProcess extends AbstractCommPageProcess {

	private static Logger log = LoggerFactory.getLogger(WeChatUpdatePageProcess.class);

	@Value("${spider.process.delay:1500:2000:3000:4000}")
	private String delay;

	public WeChatUpdatePageProcess() {
	}

	@Override
	public void process(Page page) {

		// 进入搜狗
		if (page.getUrl().get().equals(getConfig().getSpider().getStartUrl()) && !(page.getUrl().regex(getConfig().getRule().getDetailregex()).match())) {
			// *[@id="sogou_vr_11002301_box_0"]/div/div[2]/p[2]/label

			String uid = page.getHtml().xpath("//*[@id=\"main\"]/div[4]/ul/li[1]/div/div[2]/p[2]/label/text()").get();
			if (StringUtils.isNotEmpty(uid) && uid.equals(getConfig().getWechat().getUid())) {
				page.addTargetRequest(page.getHtml().xpath(getConfig().getRule().getLoadlistxpath()).links().toString() + "&f=json");
				page.getResultItems().setSkip(true);

					//page.addTargetRequest(page.getUrl().get());

			} else {

				String  msg="搜狗查询没有查询到相关公众号!";
				String seccodeImage = page.getHtml().xpath("//*[@id=\"seccodeImage\"]").get();
				if(StringUtils.isNotEmpty(seccodeImage)){
					msg="搜狗查询公众号出现验证码!";
				}
                if(!getConfig().getIsCircle()  &&  StringUtils.isNotEmpty(seccodeImage) ){
					page.addTargetRequest(page.getUrl().get());
				}
				Boolean isdynamicProxy = getConfig().getSite().getDynamicProxy();
				if (isdynamicProxy!=null &&isdynamicProxy.booleanValue()) {
					SpiderExceptionMsg spiderExceptionMsg = new SpiderExceptionMsg(SpiderExceptionEnum.LIMITNOTICE.getName(), msg, SpiderExceptionEnum.LIMITNOTICE.getValue());
					page.putField(SpiderExceptionEnum.Name, spiderExceptionMsg);
				}else {
					SpiderExceptionMsg spiderExceptionMsg = new SpiderExceptionMsg(SpiderExceptionEnum.NOTICE.getName(), msg, SpiderExceptionEnum.NOTICE.getValue());
					page.putField(SpiderExceptionEnum.Name, spiderExceptionMsg);
				}
				log.info(msg);
			}

			// page.getResultItems().setSkip(true);
		} else if (page.getUrl().toString().indexOf(getConfig().getRule().getListregex()) == 0) {

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String json = page.getRawText();
				if (json == null || json.length() <= 2) {

					log.info("微信公众号爬取异常，系统检测到爬虫，请输入验证码！");
					String msg="微信公众号爬取异常，系统检测到爬虫，请输入验证码！";
					Boolean isdynamicProxy = getConfig().getSite().getDynamicProxy();
					if (isdynamicProxy!=null &&isdynamicProxy.booleanValue()) {
						SpiderExceptionMsg spiderExceptionMsg = new SpiderExceptionMsg(SpiderExceptionEnum.LIMITNOTICE.getName(), msg, SpiderExceptionEnum.LIMITNOTICE.getValue());
						page.putField(SpiderExceptionEnum.Name, spiderExceptionMsg);
					}else {
						SpiderExceptionMsg spiderExceptionMsg = new SpiderExceptionMsg(SpiderExceptionEnum.NOTICE.getName(), msg, SpiderExceptionEnum.NOTICE.getValue());
						page.putField(SpiderExceptionEnum.Name, spiderExceptionMsg);
					}
					page.addTargetRequest(page.getUrl().get());
				} else {
					Map map = objectMapper.readValue(json, Map.class);
					if ("0".equals(map.get("ret").toString())) {
						String msg = String.valueOf(map.get("general_msg_list"));
						Map msgmap = objectMapper.readValue(msg, Map.class);
						List<Map> mapList = (List<Map>) msgmap.get("list");
						if (mapList != null && mapList.size() > 0) {
							for (Map msgMap : mapList) {
								if (msgMap.containsKey("app_msg_ext_info")) {
									Map urlmap = (Map) msgMap.get("app_msg_ext_info");
									if (urlmap.get("content_url") != null && urlmap.get("content_url").toString().length() > 0) {
										if (urlmap.get("cover") != null && urlmap.get("cover").toString().length() > 0) {
											page.addTargetRequest(urlmap.get("content_url").toString().replaceAll("&amp;", "&") + "图片" + urlmap.get("cover").toString());
										} else {
											page.addTargetRequest(urlmap.get("content_url").toString().replaceAll("&amp;", "&"));
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
					page.getResultItems().setSkip(true);
				}
			} catch (Exception e) {
				log.error("异常", e);

			}

		} else if (page.getUrl().regex(getConfig().getRule().getDetailregex()).match()) {
			// 详细页面
			setPutFile(page, getConfig().getRule());

		} else {
			page.getResultItems().setSkip(true);
		}

		String[] times = delay.split(":");
		List<Long> list = new ArrayList<Long>();
		for (String s : times) {
			try {
				Long time = Long.valueOf(s);
				list.add(time);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		if (list.size() > 0) {
			Random random=new Random();
			int i = random.nextInt(list.size());
			try {
				Thread.sleep(list.get(i));
			} catch (InterruptedException var3) {
				var3.printStackTrace();
			}
		}
		setIsCircle(page);
	}

	@Override
	public void init(WebmagicConfig config) {
		this.setConfig(config);
	}

}
