package com.qiya.boss.fwapi.api;

import javax.annotation.PostConstruct;

import com.qiya.framework.coreapi.BaseBizApi;
import com.qiya.framework.coreapi.BizApi;
import com.qiya.framework.coreapi.IBizApi;
import com.qiya.framework.middletier.model.Config;
import com.qiya.framework.middletier.service.BizConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@BizApi
public class BizConfigApi extends BaseBizApi<Config, Config, BizConfigService> implements IBizApi {
	@Autowired
	BizConfigService bizConfigService;

	@PostConstruct
	private void init() {
		this.service = bizConfigService;
		this.type = Config.class;
	}
}
