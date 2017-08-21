package com.qiya.middletier.bizenum;

/**
 * Created by qiyamac on 16/6/16.
 */
public enum SourceFromEnum {

	WXWEB("wxweb", "微信公众号"), MOBLIEWEB("mobileweb", "手机原生"), MOBLIEWEBQQ("mobileqq", "QQ第三方"), MOBLIEWEBWX("mobilewx", "微信第三方"),GUID ("guid", "唯一标示");
	private final String code;

	public String getName() {
		return name;
	}

	private final String name;

	SourceFromEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public static SourceFromEnum getEnumByCode(String code) {
		for (SourceFromEnum enumInfo : SourceFromEnum.values()) {
			if (enumInfo.code.equals(code)) {
				return enumInfo;
			}
		}

		return null;
	}
}
