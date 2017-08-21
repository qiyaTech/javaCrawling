package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

public enum BizConfigTypeEnum {
	SMS_CODE("短信", 2,"SMS_CODE"),
	USER_PWD("重置密码",3,"USER_PWD"),
//	GUYEMM("关于页面", 5,"ABOUT"),
//	SJBB("升级版本", 6,"Version"),
//	HYY("欢迎页", 7,"AppWelcome")
    COMMRISE("通用清洗",6,"COMMRISE");

	private final String name;
	private final int value;

	public String getCode() {
		return code;
	}

	private final String code;

	BizConfigTypeEnum(String name, int value, String code) {
		this.name = name;
		this.value = value;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public static BizConfigTypeEnum parse(int value) {
		for (BizConfigTypeEnum item : BizConfigTypeEnum.values()) {
			if (item.getValue() == value) {
				return item;
			}
		}
		return null;
	}

	public static BizConfigTypeEnum parse(String code) {
		for (BizConfigTypeEnum item : BizConfigTypeEnum.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}

	public static final String Name = "ConfigType";

	public static final String CodeName = "ConfigTypeCode";
	public static DataSource toDataSource() {
		DataSource ds = new DataSource();
		for (BizConfigTypeEnum item : BizConfigTypeEnum.values()) {
			ds.getList().add(new DataSourceOption(item.getName(), item.getValue()));
			ds.getMap().put(item.getValue(), item.getName());
		}
		ds.init(Name);
		return ds;
	}

	public static DataSource toCodeDataSource() {
		DataSource ds = new DataSource();
		for (BizConfigTypeEnum item : BizConfigTypeEnum.values()) {
			ds.getList().add(new DataSourceOption(item.getName(), item.getCode()));
			ds.getMap().put(item.getName(), item.getCode());
		}
		ds.init(CodeName);
		return ds;
	}
}
