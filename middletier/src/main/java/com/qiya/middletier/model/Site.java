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
站点信息
 */
@Entity
    @Table(name = "site")
public class Site implements IModel,IExportable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "site_name",columnDefinition = "NVARCHAR(200) COMMENT '站点名称'")
    private String siteName;

    @Column(name = "site_nick",columnDefinition = "NVARCHAR(50) COMMENT '站点昵称'")
    private String siteNick;

    @Column(name = "domain",columnDefinition = "NVARCHAR(50) COMMENT '域名'")
    private String domain;

    @Column(name = "head_image",columnDefinition = "NVARCHAR(200) COMMENT '头像'")
    private String headImgae;

    @Column(name = "intrduce",columnDefinition = "NVARCHAR(500) COMMENT '简介'")
    private String intrduce;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "type",columnDefinition = "int COMMENT '站点类型'")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    public Site() {
    }

    public Site(String siteName, String siteNick, String domain, Integer type) {
        this.siteName = siteName;
        this.siteNick = siteNick;
        this.domain = domain;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteNick() {
        return siteNick;
    }

    public void setSiteNick(String siteNick) {
        this.siteNick = siteNick;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHeadImgae() {
        return headImgae;
    }

    public void setHeadImgae(String headImgae) {
        this.headImgae = headImgae;
    }

    public String getIntrduce() {
        return intrduce;
    }

    public void setIntrduce(String intrduce) {
        this.intrduce = intrduce;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public void update(JsonObject jo) {
        if (jo.has("siteName"))
            this.setSiteName(jo.get("siteName").getAsString());
        if (jo.has("siteNick"))
            this.setSiteNick(jo.get("siteNick").getAsString());
        if (jo.has("domain"))
            this.setDomain(jo.get("domain").getAsString());
        if (jo.has("headImgae"))
            this.setHeadImgae(jo.get("headImgae").getAsString());
        if (jo.has("intrduce"))
            this.setIntrduce(jo.get("intrduce").getAsString());
        if (jo.has("type"))
            this.setType(jo.get("type").getAsInt());
        if (jo.has("createTime"))
            this.setCreateTime(DateUtils.toDateTime(jo.get("createTime").getAsString()));
        if (jo.has("status"))
            this.setStatus(jo.get("status").getAsInt());
    }

    @Override
    public Map<String, String> toExportMap() {
        throw new UnsupportedOperationException();
    }

    public DataSourceOption toDataSourceOption() {
        return new DataSourceOption(this.getSiteName(), this.getId().intValue());
    }

}
