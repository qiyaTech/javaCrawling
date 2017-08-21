package com.qiya.boss.fwapi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.qiya.framework.baselib.web.WebUtils;
import com.qiya.framework.def.ConstDef;
import com.qiya.framework.middletier.base.IDataSourceManager;
import com.qiya.framework.model.ApiCodeEnum;
import com.qiya.framework.model.ApiOutput;
import com.qiya.framework.model.DataSourceOption;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/ds")
public abstract class BaseDataSourceController {
	protected IDataSourceManager dataSourceManager;

	// 项目自定义
	protected abstract List<DataSourceOption> getFullList(String name);

	protected abstract List<DataSourceOption> getList(String name, Map<String, String> requestParamMap);

	protected abstract List<Long> getValue(String name, String value);

	protected abstract String getDataSourceString(String name);

	protected abstract String getValueName(String name, String value);

	/*
	 * 获取数据源列表，多层
	 */
	@RequestMapping(value = "/getfulllist/{name}", produces = { ConstDef.ApiProduces })
	public ApiOutput getFullList(HttpServletRequest request, @PathVariable String name) throws Exception {
		List<DataSourceOption> dsoList = this.getFullList(name);
		if (dsoList == null) {
			// framework注入
			dsoList = this.dataSourceManager.getFullList(name);
		}
		return new ApiOutput((dsoList == null) ? ApiCodeEnum.NOT_EXIST : dsoList);
	}

	/*
	 * 获取数据源列表：单层
	 */
	@RequestMapping(value = "/getlist/{name}", produces = { ConstDef.ApiProduces })
	public ApiOutput getList(HttpServletRequest request, @PathVariable String name) throws Exception {
		List<DataSourceOption> dsoList = this.getList(name, WebUtils.getRequestParams(request));
		if (dsoList == null) {
			// framework注入
			dsoList = this.dataSourceManager.getList(name, WebUtils.getRequestParams(request));
		}
		return new ApiOutput((dsoList == null) ? ApiCodeEnum.NOT_EXIST : dsoList);
	}

	/*
	 * 获取数据源的值
	 */
	@RequestMapping(value = "/getvalue/{name}/{value}", produces = { ConstDef.ApiProduces })
	public ApiOutput getValue(HttpServletRequest request, @PathVariable String name, @PathVariable String value) {
		List<Long> lList = this.getValue(name, value);
		if (lList == null) {
			// framework注入
			lList = this.dataSourceManager.getValue(name, value);
		}
		return new ApiOutput((lList == null) ? ApiCodeEnum.NOT_EXIST : lList);
	}

	/*
	 * 基于value获取数据源中对应的名称
	 */
	@RequestMapping(value = "/getvaluename/{name}/{value}", produces = { ConstDef.ApiProduces })
	public ApiOutput getValueName(HttpServletRequest request, @PathVariable String name, @PathVariable String value) {
		String val = this.getValueName(name, value);
		if (val == null) {
			// framework注入
			val = this.dataSourceManager.getValueName(name, value);
		}
		return new ApiOutput((val == null) ? ApiCodeEnum.NOT_EXIST : val);
	}
}
