package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

/**
 * Created by qiyamac on 16/6/16.
 */
public enum HeaderTypeEnum {

	HEADER_HEADER("header", "保存msql"),
	HEADER_COOKIE("cookie", "发送邮件");
	private final String code;

	public String getName() {
		return name;
	}

	private final String name;

	HeaderTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public static HeaderTypeEnum getEnumByCode(String code) {
		for (HeaderTypeEnum enumInfo : HeaderTypeEnum.values()) {
			if (enumInfo.code.equals(code)) {
				return enumInfo;
			}
		}

		return null;
	}

	public static final String Name = "HeaderTypeEnum";

	public static DataSource toDataSource() {
		DataSource ds = new DataSource();
		for (HeaderTypeEnum item : HeaderTypeEnum.values()) {
			ds.getList().add(new DataSourceOption(item.getName(), item.getCode()));
			ds.getMap().put(item.getCode(), item.getName());
		}
		ds.init(Name);
		return ds;
	}
}
