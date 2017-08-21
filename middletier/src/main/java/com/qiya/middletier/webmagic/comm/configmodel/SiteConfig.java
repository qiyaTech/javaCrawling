package com.qiya.middletier.webmagic.comm.configmodel;

import java.util.List;

/**
 * Created by qiyamac on 2017/3/24.
 */
public class SiteConfig {
	private String userAgent;

	private String domain;

	private int retry;

	private String charset;

	private int sleepTime;

	public Boolean getDynamicProxy() {
		return dynamicProxy;
	}

	public void setDynamicProxy(Boolean dynamicProxy) {
		this.dynamicProxy = dynamicProxy;
	}

	private Boolean dynamicProxy;;

	private List<ProxyConfig> proxy;

	private List<HeadersConfig> headers;

	private List<CookiesConfig> cookies;

	public void setUserAgent(String UserAgent) {
		this.userAgent = UserAgent;
	}

	public String getUserAgent() {
		return this.userAgent;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public int getRetry() {
		return this.retry;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public int getSleepTime() {
		return this.sleepTime;
	}

	public void setProxy(List<ProxyConfig> proxy) {
		this.proxy = proxy;
	}

	public List<ProxyConfig> getProxy() {
		return this.proxy;
	}

	public void setHeaders(List<HeadersConfig> headers) {
		this.headers = headers;
	}

	public List<HeadersConfig> getHeaders() {
		return this.headers;
	}

	public void setCookies(List<CookiesConfig> cookies) {
		this.cookies = cookies;
	}

	public List<CookiesConfig> getCookies() {
		return this.cookies;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}
