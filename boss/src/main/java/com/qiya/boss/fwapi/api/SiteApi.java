package com.qiya.boss.fwapi.api;

/**
 * Created by qiyalm on 17/3/31.
 */

import com.qiya.framework.coreapi.BaseBizApi;
import com.qiya.framework.coreapi.BizApi;
import com.qiya.framework.coreapi.IBizApi;
import com.qiya.framework.middletier.model.Config;
import com.qiya.framework.middletier.service.BizConfigService;
import com.qiya.middletier.model.Site;
import com.qiya.middletier.model.SiteResult;
import com.qiya.middletier.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@BizApi
public class SiteApi extends BaseBizApi<Site, SiteResult, SiteService> implements IBizApi {

    @Autowired
    SiteService siteService;

    @PostConstruct
    private void init() {
        this.service = siteService;
        this.type = Site.class;
    }

}
