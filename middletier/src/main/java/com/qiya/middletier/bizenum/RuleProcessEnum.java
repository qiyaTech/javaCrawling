package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

/**
 * Created by qiyamac on 16/6/16.
 */
public enum RuleProcessEnum {

	PROCESS_XPATH("XPathMartPageProcess", "xpath码市模板"),
	PROCESS_SINGLE_XPATH("XPathSinglePageProcess", "单页模板"),
	PROCESS_CSS("processcss", "css");
	private final String code;

	public String getName() {
		return name;
	}

	private final String name;

	RuleProcessEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public static RuleProcessEnum getEnumByCode(String code) {
		for (RuleProcessEnum enumInfo : RuleProcessEnum.values()) {
			if (enumInfo.code.equals(code)) {
				return enumInfo;
			}
		}

		return null;
	}

	public static final String Name = "RuleProcessEnum";

	public static DataSource toDataSource() {
		DataSource ds = new DataSource();
		for (RuleProcessEnum item : RuleProcessEnum.values()) {
			ds.getList().add(new DataSourceOption(item.getName(), item.getCode()));
			ds.getMap().put(item.getCode(), item.getName());
		}
		ds.init(Name);
		return ds;
	}
}
