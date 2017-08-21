package com.qiya.middletier.webmagic.process;

import java.text.SimpleDateFormat;
import java.util.*;

import com.qiya.framework.coreservice.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiya.framework.baselib.util.base.DateUtils;
import com.qiya.middletier.bizenum.TaskPropressEnum;
import com.qiya.middletier.webmagic.comm.configmodel.*;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.exceptions.JedisException;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by qiyamac on 2017/3/24.
 */
public abstract class AbstractCommPageProcess implements PageProcessor {

	private static Logger log = LoggerFactory.getLogger(AbstractCommPageProcess.class);


	private static final String STRURLRUN_PREFIX = "starturl_";

	protected String uuid;

	public WebmagicConfig getConfig() {
		return config;
	}

	public void setConfig(WebmagicConfig config) {
		this.config = config;
	}

	private WebmagicConfig config;

	public List<RinseRule> getCommRinseRules() {
		return commRinseRules;
	}

	public void setCommRinseRules(List<RinseRule> rinseRules) {
		this.commRinseRules = rinseRules;
	}

	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}

	private List<RinseRule> commRinseRules;


	@Autowired
	private RedisService redisService;

	public abstract void init(WebmagicConfig config);

	@Override
	public Site getSite() {

		SiteConfig siteConfig = this.config.getSite();
		Site site = Site.me().setDomain(siteConfig.getDomain()).setTimeOut(5000).setSleepTime(siteConfig.getSleepTime())
				.setUserAgent(siteConfig.getUserAgent()).setCycleRetryTimes(siteConfig.getRetry()).setRetryTimes(siteConfig.getRetry())
				.setRetrySleepTime(siteConfig.getSleepTime());

		if (siteConfig.getCharset() != null) {
			site.setCharset(siteConfig.getCharset());
		}

		List<HeadersConfig> headersConfigList = siteConfig.getHeaders();
		if (headersConfigList != null) {
			for (HeadersConfig header : headersConfigList) {
				site.addHeader(header.getName(), header.getValue());
			}
		}
		List<CookiesConfig> cookiesConfigs = siteConfig.getCookies();
		if (cookiesConfigs != null) {
			for (CookiesConfig cookie : cookiesConfigs) {
				site.addCookie(cookie.getName(), cookie.getValue());
			}
		}

		List<ProxyConfig> proxyConfigs = siteConfig.getProxy();
		if (proxyConfigs != null && proxyConfigs.size() == 1) {
			ProxyConfig proxyConfig = new ProxyConfig();
			site.setHttpProxy(new HttpHost(proxyConfig.getAddress(), proxyConfig.getProt()));
			// 设置代理的认证
			if (StringUtils.isNotBlank(proxyConfig.getUsername()) && StringUtils.isNotBlank(proxyConfig.getPassword())) {
				site.setUsernamePasswordCredentials(new UsernamePasswordCredentials(proxyConfig.getUsername(), proxyConfig.getPassword()));
			}

		} else if (proxyConfigs != null && proxyConfigs.size() > 1) {
			List<String[]> proxyList = new ArrayList<String[]>();
			for (ProxyConfig proxyConfig : proxyConfigs) {
				proxyList.add(new String[] { proxyConfig.getName(), proxyConfig.getPassword(), proxyConfig.getAddress(), String.valueOf(proxyConfig.getProt()) });
			}
			site.setHttpProxyPool(proxyList, false);
		}
		return site;

	}

	public Page setPutFile(Page page, RuleMatchConfig ruleMatchConfig) {
		setRinseRule(page);
		setCommRinseRule(page);
		Date startTime = null;
		Date endTime = null;

		// 获取判断开始结束时间
		Map condition = config.getCondition();
		if (condition != null) {
			startTime = condition.get("startTime") == null ? null : DateUtils.toDate(condition.get("startTime").toString());
			endTime = condition.get("endTime") == null ? null : DateUtils.toDate(condition.get("endTime").toString());
		}

		SpiderConfig spiderConfig = config.getSpider();
		page.putField("siteId", Long.valueOf(spiderConfig.getSiteid()));

		if (ruleMatchConfig.getDetailxpath() == null) {
			return page;
		}
		for (DetailxpathConfig rule : ruleMatchConfig.getDetailxpath()) {
			// 处理时间
			if (StringUtils.isNotEmpty(rule.getSimpleDateFormat())) {
				String publishDate;
				Date publishTime;

				try {
					if (StringUtils.isNotEmpty(rule.getValue())) {
						Selectable node = page.getHtml().xpath(rule.getValue());
						if (StringUtils.isNotEmpty(rule.getReg())) {
							node = node.regex(rule.getReg());
						}
						publishDate = node.toString();
					} else {
						publishDate = page.getHtml().regex(rule.getReg()).toString();
					}

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(rule.getSimpleDateFormat());
					publishTime = simpleDateFormat.parse(publishDate);
					// 如果时间没有包含年份,则默认使用当前年
					if (!simpleDateFormat.toPattern().contains("yyyy")) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(publishTime);
						calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
						publishTime = calendar.getTime();
					}

				} catch (Exception e) {
					// 解析错误当前时间
					publishTime = new Date();
				}

				page.putField(rule.getName(), publishTime);

				// 判断开始结束时间
				if (startTime != null && endTime != null) {
					Integer timeStatus = TaskPropressEnum.SAVEDB.getValue();
					Date pulishDate1 = DateUtils.toDate(DateUtils.toDateString(publishTime));

					if (pulishDate1.getTime() > endTime.getTime() && pulishDate1.getTime() > startTime.getTime()) {
						timeStatus = TaskPropressEnum.CONTINUE.getValue();
						log.info("爬取历史文章日期未达到指定时间范围,需继续爬.");

					} else if (pulishDate1.getTime() < startTime.getTime() && pulishDate1.getTime() < endTime.getTime()) {
						timeStatus = TaskPropressEnum.TASKSTOP.getValue();
						log.info("爬取历史文章日期已超出开始时间范围.");

					} else {
						log.info("爬取历史文章日期在指定范围内.");
					}

					page.putField("taskStatus", timeStatus);
				}

			} else {
				Selectable node;
				if (StringUtils.isNotEmpty(rule.getValue())) {
					node = page.getHtml().xpath(rule.getValue());
					if (StringUtils.isNotEmpty(rule.getReg())) {
						node = node.regex(rule.getReg());
					}
					page.putField(rule.getName(), node);
				} else {
					page.putField(rule.getName(), page.getHtml().regex(rule.getReg()).toString());
				}
			}

		}
		return page;

	}

	public void setRinseRule(Page page) {
		List<RinseRule> rinseRules = getConfig().getRinseRules();
		if (rinseRules != null) {
			page.putField("rinseRules", rinseRules);
		}
	}

	public void setCommRinseRule(Page page) {

		if (commRinseRules != null) {
			page.putField("commRinseRules", commRinseRules);
		}
	}

	public void setIsCircle(Page page) {
		Boolean isCircle = config.getIsCircle();
		if (isCircle) {
			String startRun = redisService.get(getStrurlrunKey());
			if (startRun != null && "true".equals(startRun)) {

				if (page.getUrl().toString().equals(config.getSpider().getStartUrl())) {
					try {

						Long interval=   config.getCircleInterval();
						if(interval==null){
							interval=1000*60*60l;
						}else {
							interval=interval*1000;
						}
						log.info(uuid+"爬取任务完成，休眠"+interval+"毫秒后后爬取！");
						Thread.sleep(interval);
						log.info(uuid+"爬取任务休眠结束");

					} catch (Exception e) {
						log.error("休眠异常：",e);
					}finally {
						cleanStrurlrunKey();
						page.getResultItems().setSkip(true);
						page.getTargetRequests().clear();
						page.addTargetRequest(new Request(getConfig().getSpider().getStartUrl()).setPriority(-999999));
					}
				}
			} else {
				if(page.getUrl().toString().equals(config.getSpider().getStartUrl())){
					redisService.set(getStrurlrunKey(), "true");
				}

			}
			if(page.getTargetRequests().size()==0){
				page.addTargetRequest(new Request(getConfig().getSpider().getStartUrl()).setPriority(-999999));
			}

		}

	}

	public String getStrurlrunKey() {
		return STRURLRUN_PREFIX + uuid;
	}

	public void cleanStrurlrunKey() {
		try {
			redisService.del(getStrurlrunKey());
		}catch (JedisException e){
			log.error("获取jedis错误",e);
			log.error("getNumActive:" +redisService.getPool().getNumActive());
			log.error("getNumWaiters:" + redisService.getPool().getNumWaiters());
			log.error("getNumIdle:" + redisService.getPool().getNumIdle());
			throw e;
		}

	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
