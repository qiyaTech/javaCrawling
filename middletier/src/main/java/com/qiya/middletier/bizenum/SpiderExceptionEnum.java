package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

/**
 * Created by qiyalm on 17/3/30.
 */
public enum SpiderExceptionEnum {

    NOTICE(1, "爬虫任务异常通知"),
    EXIT(2,"退出"),
    LIMITNOTICE(3, "爬虫任务异常阀值通知");


    private final Integer value;
    public String getName() {
        return name;
    }

    private final String name;

    SpiderExceptionEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public static SpiderExceptionEnum getEnumByCode(String code) {
        for (SpiderExceptionEnum enumInfo : SpiderExceptionEnum.values()) {
            if (enumInfo.value.equals(code)) {
                return enumInfo;
            }
        }

        return null;
    }

    public static final String Name = "SpiderException";

    public static DataSource toDataSource() {
        DataSource ds = new DataSource();
        for (SpiderExceptionEnum item : SpiderExceptionEnum.values()) {
            ds.getList().add(new DataSourceOption(item.getName(), item.getValue()));
            ds.getMap().put(item.getValue(), item.getName());
        }
        ds.init(Name);
        return ds;
    }

}
