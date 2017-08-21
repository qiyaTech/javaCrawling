package com.qiya.middletier.webmagic.comm.configmodel;

/**
 * Created by qiyamac on 2017/4/6.
 */
public class SpiderExceptionMsg {

	private String title;
	private String msg;
	private Integer type;

    public SpiderExceptionMsg(String title, String msg, Integer type) {
        this.title = title;
        this.msg = msg;
        this.type = type;
    }

    public SpiderExceptionMsg() {
    }

    public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
