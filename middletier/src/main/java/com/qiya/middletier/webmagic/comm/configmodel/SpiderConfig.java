package com.qiya.middletier.webmagic.comm.configmodel;

import java.util.List;

/**
 * Created by qiyamac on 2017/3/24.
 */
public class SpiderConfig {
	private String siteid;

	private Integer thread;

	private String processer;

	private List<String> pipeline;

	private String downloader;

	private String startUrl;

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public Integer getThread() {
		return thread;
	}

	public void setThread(Integer thread) {
		this.thread = thread;
	}

	public String getProcesser() {
		return processer;
	}

	public void setProcesser(String processer) {
		this.processer = processer;
	}

	public List<String> getPipeline() {
		return pipeline;
	}

	public void setPipeline(List<String> pipeline) {
		this.pipeline = pipeline;
	}

	public String getDownloader() {
		return downloader;
	}

	public void setDownloader(String downloader) {
		this.downloader = downloader;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}
}
