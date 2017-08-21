package com.qiya.middletier.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.JsonObject;
import com.qiya.framework.baselib.util.base.DateUtils;
import com.qiya.framework.coreservice.CustomDateTimeSerializer;
import com.qiya.framework.middletier.base.IExportable;
import com.qiya.framework.middletier.base.IModel;
import com.qiya.framework.model.DataSourceOption;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * Created by qiyalm on 17/3/24.
 */
/*
任务
 */
@Entity
@Table(name = "task")
public class Task implements IModel,IExportable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "site_id",columnDefinition = "bigint COMMENT '站点Id'")
    private Long siteId;

    @Column(name = "task_name",columnDefinition = "NVARCHAR(100) COMMENT '任务名称'")
    private String taskName;

    @Column(name = "run_state",columnDefinition = "NVARCHAR(20) COMMENT '运行状态:running(运行中);stop(已结束)'")
    private String runState;

    @Column(name = "timer_task",columnDefinition = "NVARCHAR(20) COMMENT '是否定时任务:是(1);否(2)'")
    private Integer timerTask;

    @Column(name = "run_task",columnDefinition = "NVARCHAR(20) COMMENT '需要执行任务:是(1);否(2)'")
    private Integer runTask;

    @Column(name = "start_time",columnDefinition = "datetime COMMENT '开始时间'")
    private Date startTime;

    @Column(name = "end_time",columnDefinition = "datetime COMMENT '结束时间'")
    private Date endTime;

    @Column(name = "task_rule_json",columnDefinition = "MEDIUMTEXT COMMENT '任务规则json'")
    private String taskRuleJson;

    @Column(name = "sprider_uuid",columnDefinition = "NVARCHAR(50) COMMENT '任务启动UUID'")
    private String spiderUUID;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "status")
    private Integer status;


    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getRunState() {
        return runState;
    }

    public void setRunState(String runState) {
        this.runState = runState;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTaskRuleJson() {
        return taskRuleJson;
    }

    public void setTaskRuleJson(String taskRuleJson) {
        this.taskRuleJson = taskRuleJson;
    }

    public String getSpiderUUID() {
        return spiderUUID;
    }

    public void setSpiderUUID(String spiderUUID) {
        this.spiderUUID = spiderUUID;
    }

    @Override
    public void update(JsonObject jo) {
        if (jo.has("siteId"))
            this.setSiteId(jo.get("siteId").getAsLong());
        if (jo.has("taskName"))
            this.setTaskName(jo.get("taskName").getAsString());
        if (jo.has("runState"))
            this.setRunState(jo.get("runState").getAsString());
        if (jo.has("startTime"))
            this.setStartTime(DateUtils.toDateTime(jo.get("startTime").getAsString()));
        if (jo.has("endTime"))
            this.setEndTime(DateUtils.toDateTime(jo.get("endTime").getAsString()));
        if (jo.has("taskRuleJson"))
            this.setTaskRuleJson(jo.get("taskRuleJson").getAsString());
        if (jo.has("createTime"))
            this.setCreateTime(DateUtils.toDateTime(jo.get("createTime").getAsString()));
        if (jo.has("status"))
            this.setStatus(jo.get("status").getAsInt());
        if (jo.has("runTask"))
            this.setRunTask(jo.get("runTask").getAsInt());
        if (jo.has("timerTask"))
            this.setTimerTask(jo.get("timerTask").getAsInt());
    }

    @Override
    public Map<String, String> toExportMap() {
        throw new UnsupportedOperationException();
    }

    public Integer getTimerTask() {
        return timerTask;
    }

    public void setTimerTask(Integer timerTask) {
        this.timerTask = timerTask;
    }

    public Integer getRunTask() {
        return runTask;
    }

    public void setRunTask(Integer runTask) {
        this.runTask = runTask;
    }
}
