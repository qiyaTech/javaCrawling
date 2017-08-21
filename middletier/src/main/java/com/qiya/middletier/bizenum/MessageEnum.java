package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

public enum MessageEnum {
	SYS("系统", 1);

	private final String name;
	private final int value;

	MessageEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public static MessageEnum parse(int value) {
		for (MessageEnum item : MessageEnum.values()) {
			if (item.getValue() == value) {
				return item;
			}
		}
		return null;
	}

	public static final String Name = "CmsCategoryType";

	public static DataSource toDataSource() {
		DataSource ds = new DataSource();
		for (MessageEnum item : MessageEnum.values()) {
			ds.getList().add(new DataSourceOption(item.getName(), item.getValue()));
			ds.getMap().put(item.getValue(), item.getName());
		}
		ds.init(Name);
		return ds;
	}
}
