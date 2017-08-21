package com.qiya.middletier.webmagic.comm.configmodel;

import java.util.List;

/**
 * Created by qiyamac on 2017/3/24.
 */
public class RuleMatchConfig {
	private String listregex;

	private String listxpath;

	private String detailregex;

	private String loadlistxpath;

	private String itemxpath;

	private String itemImagexpath;

	private List<DetailxpathConfig> detailxpath;

	public void setListregex(String listregex) {
		this.listregex = listregex;
	}

	public String getListregex() {
		return this.listregex;
	}

	public void setListxpath(String listxpath) {
		this.listxpath = listxpath;
	}

	public String getListxpath() {
		return this.listxpath;
	}

	public void setDetailregex(String detailregex) {
		this.detailregex = detailregex;
	}

	public String getDetailregex() {
		return this.detailregex;
	}

	public void setDetailxpath(List<DetailxpathConfig> detailxpath) {
		this.detailxpath = detailxpath;
	}

	public List<DetailxpathConfig> getDetailxpath() {
		return this.detailxpath;
	}

	public String getLoadlistxpath() {
		return loadlistxpath;
	}

	public void setLoadlistxpath(String loadlistxpath) {
		this.loadlistxpath = loadlistxpath;
	}

	public String getItemxpath() {
		return itemxpath;
	}

	public void setItemxpath(String itemxpath) {
		this.itemxpath = itemxpath;
	}

	public String getItemImagexpath() {
		return itemImagexpath;
	}

	public void setItemImagexpath(String itemImagexpath) {
		this.itemImagexpath = itemImagexpath;
	}
}
