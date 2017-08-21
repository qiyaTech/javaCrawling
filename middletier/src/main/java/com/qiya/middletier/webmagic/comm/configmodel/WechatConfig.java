package com.qiya.middletier.webmagic.comm.configmodel;

/**
 * Created by qiyamac on 2017/3/24.
 */
public class WechatConfig {
	private String name;

	private String uid;

	private String url;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}
}
