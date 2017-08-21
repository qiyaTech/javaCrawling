package com.qiya.middletier.webmagic.comm.configmodel;

public class RinseRule {

	private String action;
	private String type;
	private String cssquery;
	private String name;
	private String value;
	private String source;
	private String target;

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setCssquery(String cssquery) {
		this.cssquery = cssquery;
	}

	public String getCssquery() {
		return cssquery;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}