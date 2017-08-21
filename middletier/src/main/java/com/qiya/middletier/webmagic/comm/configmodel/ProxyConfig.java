package com.qiya.middletier.webmagic.comm.configmodel;

/**
 * Created by qiyamac on 2017/3/24.
 */
public class ProxyConfig {
	private String name;

	private String address;

	private int prot;

	private String schema;

	private String username;

	private String password;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setProt(int prot) {
		this.prot = prot;
	}

	public int getProt() {
		return this.prot;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getSchema() {
		return this.schema;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}
}
