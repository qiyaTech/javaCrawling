package com.qiya.boss.fwapi.api;

import com.qiya.framework.coreapi.BaseBizApi;
import com.qiya.framework.coreapi.BizApi;
import com.qiya.framework.coreapi.IBizApi;
import com.qiya.framework.middletier.model.Config;
import com.qiya.framework.middletier.service.BizConfigService;
import com.qiya.middletier.model.Site;
import com.qiya.middletier.model.SiteResult;
import com.qiya.middletier.model.Task;
import com.qiya.middletier.model.TaskResult;
import com.qiya.middletier.service.SiteService;
import com.qiya.middletier.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by qiyalm on 17/3/31.
 */

@Component
@BizApi
public class TaskApi extends BaseBizApi<Task, TaskResult, TaskService> implements IBizApi {

    @Autowired
    TaskService taskService;

    @PostConstruct
    private void init() {
        this.service = taskService;
        this.type = Task.class;
    }

}
