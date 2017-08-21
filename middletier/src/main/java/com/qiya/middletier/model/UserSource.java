package com.qiya.middletier.model;

import javax.validation.constraints.NotNull;

import com.google.gson.JsonObject;
import com.qiya.framework.middletier.base.IModel;
import javax.persistence.*;
/**
 *  用户来源
 */
@Entity
@Table(name = "user_source")
public class UserSource implements IModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "user_id")
	private Long userId;

	@NotNull
	@Column(name = "source_from")
	private String sourceFrom;

	@NotNull
	@Column(name = "open_id", columnDefinition = "NVARCHAR(100)")
	private String openId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public void update(JsonObject jo) {
		if (jo.has("userId"))
			this.setUserId(jo.get("userId").getAsLong());
		if (jo.has("sourceFrom"))
			this.setSourceFrom(jo.get("sourceFrom").getAsString());
		if (jo.has("openId"))
			this.setOpenId(jo.get("openId").getAsString());

	}

}
