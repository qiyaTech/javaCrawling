package com.qiya.middletier.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.JsonObject;
import com.qiya.framework.coreservice.CustomDateTimeSerializer;
import com.qiya.framework.middletier.base.IModel;


/**
 * Created by qiyamac on 2017/3/7.
 */
@Entity
@Table(name = "sys_message")
public class SysMessage implements IModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @NotNull
    @Column(name = "type", columnDefinition = "int COMMENT '消息类型'")
    private Integer type;


	@Column(name = "title", columnDefinition = "NVARCHAR(200) COMMENT '标题'")
	private String title;

	@NotNull
	@Column(name = "context", columnDefinition = "NVARCHAR(1024) COMMENT '内容'")
	private String context;

	@Column(name = "url", columnDefinition = "NVARCHAR(1024) COMMENT 'url地址'")
	private String url;


	@Column(name = "send_time", columnDefinition = "Date  COMMENT '发送时间'")
	private Date sendTime;


	@Column(name = "receiver_id", columnDefinition = "bigint COMMENT '接收者id'")
	private Long receiverId;


	@Column(name = "receiver_role_id", columnDefinition = "bigint COMMENT '接收者角色id'")
	private Long receiverRoleId;

	@NotNull
	@Column(name = "send_id", columnDefinition = "bigint COMMENT '发送者id 发送id为0'")
	private Long senderId;


    @NotNull
    private int status;

	@NotNull
	private Date createtime;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public SysMessage() {
	}

	@Override
	public void update(JsonObject jo) {

	}

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

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Long getReceiverRoleId() {
		return receiverRoleId;
	}

	public void setReceiverRoleId(Long receiverRoleId) {
		this.receiverRoleId = receiverRoleId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
