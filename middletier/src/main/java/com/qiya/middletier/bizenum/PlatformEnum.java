package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

/**
 * Created by qiyalm on 17/5/16.
 */
public enum  PlatformEnum {

    WECHAT("微信", 1,"wechat"),
    APP("app",2,"app"),
    WEB("网页",3,"web")
    ;

    private final String name;
    private final int value;

    public String getCode() {
        return code;
    }

    private final String code;

    PlatformEnum(String name, int value, String code) {
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

    public static PlatformEnum parse(int value) {
        for (PlatformEnum item : PlatformEnum.values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return null;
    }

    public static PlatformEnum parse(String code) {
        for (PlatformEnum item : PlatformEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static final String Name = "platform";

    public static final String CodeName = "platform";
    public static DataSource toDataSource() {
        DataSource ds = new DataSource();
        for (PlatformEnum item : PlatformEnum.values()) {
            ds.getList().add(new DataSourceOption(item.getCode(), item.getValue()));
            ds.getMap().put(item.getValue(), item.getCode());
        }
        ds.init(Name);
        return ds;
    }

    public static DataSource toCodeDataSource() {
        DataSource ds = new DataSource();
        for (PlatformEnum item : PlatformEnum.values()) {
            ds.getList().add(new DataSourceOption(item.getName(), item.getCode()));
            ds.getMap().put(item.getName(), item.getCode());
        }
        ds.init(CodeName);
        return ds;
    }

}
