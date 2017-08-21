package com.qiya.middletier.model;
import com.google.gson.JsonObject;
import com.qiya.framework.middletier.base.IModel;
import javax.persistence.*;
import java.util.Date;
/**
 * Created by qiyalm on 17/3/29.
 */
@Entity
@Table(name = "article_detail")
public class ArticleDetail implements IModel {
    @Id
    private Long id;
    @Column(name = "content",columnDefinition = "MEDIUMTEXT COMMENT '文章内容'")
    private String content;
    @Column(name = "before_content",columnDefinition = "MEDIUMTEXT COMMENT '原文内容'")
    private String beforeContent;
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getBeforeContent() {
        return beforeContent;
    }
    public void setBeforeContent(String beforeContent) {
        this.beforeContent = beforeContent;
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
    @Override
    public void update(JsonObject jo) {
    }
}