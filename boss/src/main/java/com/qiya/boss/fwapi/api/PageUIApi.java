package com.qiya.boss.fwapi.api;

import javax.annotation.PostConstruct;

import com.qiya.framework.coreapi.BaseBizApi;
import com.qiya.framework.coreapi.BizApi;
import com.qiya.framework.coreapi.IBizApi;
import com.qiya.framework.middletier.model.PageUI;
import com.qiya.framework.middletier.service.PageUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@BizApi
public class PageUIApi extends BaseBizApi<PageUI, PageUI, PageUIService> implements IBizApi {
	@Autowired
	PageUIService pageUIService;

	@PostConstruct
	private void init() {
		this.service = pageUIService;
		this.type = PageUI.class;
	}
}
