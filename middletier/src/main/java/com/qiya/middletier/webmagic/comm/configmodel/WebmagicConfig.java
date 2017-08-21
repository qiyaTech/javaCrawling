package com.qiya.middletier.webmagic.comm.configmodel;

import java.util.*;

/**
 * Created by qiyamac on 2017/3/24.
 */
public class WebmagicConfig {
	private SiteConfig site;

	private SpiderConfig spider;

	private Boolean isCircle;

	private Long circleInterval;

	private Map condition;

	private WechatConfig wechat;

	private RuleMatchConfig rule;

	private List<RinseRule> rinseRules;

	public void setSite(SiteConfig site) {
		this.site = site;
	}

	public SiteConfig getSite() {
		return this.site;
	}

	public void setSpider(SpiderConfig spider) {
		this.spider = spider;
	}

	public SpiderConfig getSpider() {
		return this.spider;
	}

	public void setIsCircle(Boolean isCircle) {
		this.isCircle = isCircle;
	}

	public Boolean getIsCircle() {
		return this.isCircle;
	}

	public void setCondition(Map condition) {
		this.condition = condition;
	}

	public Map getCondition() {
		return this.condition;
	}

	public void setWechat(WechatConfig wechat) {
		this.wechat = wechat;
	}

	public WechatConfig getWechat() {
		return this.wechat;
	}

	public void setRule(RuleMatchConfig rule) {
		this.rule = rule;
	}

	public RuleMatchConfig getRule() {
		return this.rule;
	}

	public List<RinseRule> getRinseRules() {
		return rinseRules;
	}

	public void setRinseRules(List<RinseRule> rinseRules) {
		this.rinseRules = rinseRules;
	}

	public Long getCircleInterval() {
		return circleInterval;
	}

	public void setCircleInterval(Long circleInterval) {
		this.circleInterval = circleInterval;
	}
}
