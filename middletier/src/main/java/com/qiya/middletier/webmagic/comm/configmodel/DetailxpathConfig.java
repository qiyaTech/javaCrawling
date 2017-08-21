package com.qiya.middletier.webmagic.comm.configmodel;

/**
 * Created by qiyamac on 2017/3/24.
 */
public class DetailxpathConfig {
	private String name;

	private String reg;

	private String value;

	private String simpleDateFormat;

	public DetailxpathConfig(String name, String reg, String value) {
		this.name = name;
		this.reg = reg;
		this.value = value;
	}

	public DetailxpathConfig(String name,  String value) {
		this.name = name;
		this.value = value;
	}
	public DetailxpathConfig() {
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getReg() {
		return this.reg;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String getSimpleDateFormat() {
		return simpleDateFormat;
	}

	public void setSimpleDateFormat(String simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
}
