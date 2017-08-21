package com.qiya.middletier.webmagic.downloader;

import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;

import us.codecraft.webmagic.downloader.AbstractDownloader;

/**
 * Created by qiyamac on 2017/5/10.
 */
public  abstract class CustomAbstractDownloader extends AbstractDownloader {


    public WebmagicConfig getConfig() {
        return config;
    }

    public void setConfig(WebmagicConfig config) {
        this.config = config;
    }

    private WebmagicConfig config;

}
