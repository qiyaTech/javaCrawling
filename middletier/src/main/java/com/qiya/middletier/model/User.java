package com.qiya.middletier.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.JsonObject;
import com.qiya.framework.coreservice.CustomDateTimeSerializer;
import com.qiya.framework.middletier.base.IModel;
import javax.persistence.*;

/**
 * Created by dengduiyi on 16/6/7.
 */
@Entity
@Table(name = "user")
public class User implements IModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "mobile", columnDefinition = "NVARCHAR(30)")
	private String mobile;

	@Column(name = "password", columnDefinition = "NVARCHAR(50)")
	private String password;

	@Column(name = "name", columnDefinition = "NVARCHAR(100)")
	private String name;

	@Column(name = "sex", columnDefinition = "NVARCHAR(2)")
	private String sex;

	@Column(name = "address", columnDefinition = "NVARCHAR(200)")
	private String address;

	@Column(name = "head_image", columnDefinition = "NVARCHAR(1000)")
	private String headImage;

	@Column(name = "pms_Mobile", columnDefinition = "NVARCHAR(30)")
	private String pmsMobile;

	@NotNull
	private int status;

	@NotNull
	private Date createtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getPmsMobile() {
		return pmsMobile;
	}

	public void setPmsMobile(String pmsMobile) {
		this.pmsMobile = pmsMobile;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public void update(JsonObject jo) {
		if (jo.has("mobile"))
			this.setMobile(jo.get("mobile").getAsString());
		if (jo.has("password"))
			this.setPassword(jo.get("password").getAsString());
		if (jo.has("name"))
			this.setName(jo.get("name").getAsString());
		if (jo.has("sex"))
			this.setSex(jo.get("sex").getAsString());
		if (jo.has("address"))
			this.setAddress(jo.get("address").getAsString());
		if (jo.has("headImage"))
			this.setHeadImage(jo.get("headImage").getAsString());
		if (jo.has("status"))
			this.setStatus(jo.get("status").getAsInt());
		if (jo.has("pmsMobile"))
			this.setStatus(jo.get("pmsMobile").getAsInt());

	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", mobile='" + mobile + '\'' + ", password='" + password + '\'' + ", name='" + name + '\'' + ", sex='" + sex + '\'' + ", address='" + address + '\'' + ", headImage='" + headImage + '\'' + ", status=" + status + ", createtime=" + createtime + '}';
	}
}
