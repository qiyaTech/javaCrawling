package com.qiya.middletier.model;
import com.google.gson.JsonObject;
import com.qiya.framework.baselib.util.base.DateUtils;
import com.qiya.framework.middletier.base.IModel;
import javax.persistence.*;
import java.util.Date;
/**
 * Created by qiyalm on 17/3/24.
 * 文章
 */
@Entity
@Table(name = "article")
public class Article implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "site_id",columnDefinition = "bigint COMMENT '站点Id'")
    private Long siteId;
    @Column(name = "title",columnDefinition = "NVARCHAR(100) COMMENT '标题'")
    private String title;
    @Column(name = "introduce",columnDefinition = "NVARCHAR(500) COMMENT '简介'")
    private String introduce;
    @Column(name = "author",columnDefinition = "NVARCHAR(50) COMMENT '作者'")
    private String author;
    @Column(name = "pic",columnDefinition = "NVARCHAR(200) COMMENT '封面图片'")
    private String pic;
    @Column(name = "public_time",columnDefinition = "datetime COMMENT '发布时间'")
    private Date publicTime;
    @Column(name = "link_url",columnDefinition = "NVARCHAR(200) COMMENT '原文链接地址'")
    private String linkUrl;
    @Column(name = "good_count",columnDefinition = "int COMMENT '点赞数'")
    private Integer goodCount;
    @Column(name = "read_count",columnDefinition = "int COMMENT '阅读数'")
    private Integer readCount;
    @Column(name = "create_time",columnDefinition = "datetime COMMENT '创建时间'")
    private Date createTime;
    @Column(name = "status")
    private Integer status;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getIntroduce() {
        return introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Date getPublicTime() {
        return publicTime;
    }
    public void setPublicTime(Date publicTime) {
        this.publicTime = publicTime;
    }
    public String getLinkUrl() {
        return linkUrl;
    }
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
    public Integer getGoodCount() {
        return goodCount;
    }
    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }
    public Integer getReadCount() {
        return readCount;
    }
    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }
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
    public Long getSiteId() {
        return siteId;
    }
    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    @Override
    public void update(JsonObject jo) {
        if (jo.has("siteId"))
            this.setSiteId(jo.get("siteId").getAsLong());
        if (jo.has("title"))
            this.setTitle(jo.get("title").toString());
        if (jo.has("introduce"))
            this.setIntroduce(jo.get("introduce").toString());
        if (jo.has("author"))
            this.setAuthor(jo.get("author").toString());
        if (jo.has("pic"))
            this.setPic(jo.get("pic").toString());
        if (jo.has("linkUrl"))
            this.setLinkUrl(jo.get("linkUrl").toString());
        if (jo.has("goodCount"))
            this.setGoodCount(jo.get("goodCount").getAsInt());
        if (jo.has("readCount"))
            this.setReadCount(jo.get("readCount").getAsInt());
        if (jo.has("publicTime"))
            this.setPublicTime(DateUtils.toDateTime(jo.get("publicTime").toString()));
        if (jo.has("createTime"))
            this.setCreateTime(DateUtils.toDateTime(jo.get("createTime").toString()));
        if (jo.has("status"))
            this.setStatus(jo.get("status").getAsInt());
    }
}