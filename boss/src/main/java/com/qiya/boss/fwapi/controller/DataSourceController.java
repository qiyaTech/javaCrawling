package com.qiya.boss.fwapi.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.qiya.framework.baselib.web.WebUtils;
import com.qiya.framework.def.ConstDef;
import com.qiya.framework.model.ApiCodeEnum;
import com.qiya.framework.model.ApiOutput;
import com.qiya.framework.model.DataSourceOption;
import com.qiya.middletier.manager.DataSourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ds")
public class DataSourceController extends BaseDataSourceController {
    @Autowired
    DataSourceManager dsManager;

    @PostConstruct
    private void init() {
        this.dataSourceManager = this.dsManager;
    }

    protected List<DataSourceOption> getFullList(String name) {
        return null;
    }

    protected List<DataSourceOption> getList(String name, Map<String, String> requestParamMap) {
        return null;
    }

    protected List<Long> getValue(String name, String value) {
        return null;
    }

    protected String getDataSourceString(String name) {
        return null;
    }

    protected String getValueName(String name, String value) {
        return null;
    }

    /*
     * 获取数据源列表，多层
     */
    @RequestMapping(value = "/getfulllist/{name}/{value}", produces = { ConstDef.ApiProduces })
    public ApiOutput getFullList(HttpServletRequest request, @PathVariable String name, @PathVariable String value) throws Exception {
        switch (name) {
        case "InfoCategory":
//            List<DataSourceOption> dsoInfoList = this.infoCategoryService.getFullDataSourceList(value);
//            return new ApiOutput((dsoInfoList == null) ? ApiCodeEnum.NOT_EXIST : dsoInfoList);
        // 项目自定义
        default:
            // framework注入
            List<DataSourceOption> dsoList = this.dataSourceManager.getFullList(name);
            return new ApiOutput((dsoList == null) ? ApiCodeEnum.NOT_EXIST : dsoList);
        }
    }

    @RequestMapping(value = "/getlist/{name}", produces = { ConstDef.ApiProduces })
    public ApiOutput getList(HttpServletRequest request, @PathVariable String name) throws Exception {
        List<DataSourceOption> dsoList = this.getList(name, WebUtils.getRequestParams(request));
        // framework注入
        if (dsoList == null) {
            // framework注入
            dsoList = this.dataSourceManager.getList(name, WebUtils.getRequestParams(request));
        }
        return new ApiOutput((dsoList == null) ? ApiCodeEnum.NOT_EXIST : dsoList);
    }

}
