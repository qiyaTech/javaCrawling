package com.qiya.middletier.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.qiya.middletier.bizenum.*;
import com.qiya.middletier.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiya.framework.middletier.base.BaseDataSourceManager;
import com.qiya.framework.middletier.base.IDataSourceManager;
import com.qiya.framework.model.DataSourceOption;

/*
 * 静态数据源
 */
@Service
public class DataSourceManager extends BaseDataSourceManager implements IDataSourceManager {

	@Autowired
	SiteService siteService;

	@PostConstruct
	private void init() {
		// framework注入
		this.baseInit();

		// 项目自定义
		this.map.put(BizConfigTypeEnum.Name, BizConfigTypeEnum.toDataSource());
		this.map.put(RuleProcessEnum.Name, RuleProcessEnum.toDataSource());
		this.map.put(SiteType.Name, SiteType.toDataSource());
		this.map.put(TaskStatusEnum.Name, TaskStatusEnum.toDataSource());
		this.map.put(BizConfigTypeEnum.CodeName, BizConfigTypeEnum.toCodeDataSource());

	}

	/*
	 * 获取数据源列表：单层
	 */
	public List<DataSourceOption> getList(String name, Map<String, String> requestParamMap) {
		switch (name) {
			case "Site":
				return siteService.getDataSourceList();

			default:
				return super.getList(name ,requestParamMap);
		}
	}

}
