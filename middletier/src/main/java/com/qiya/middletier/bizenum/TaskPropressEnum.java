package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

/**
 * Created by qiyalm on 17/3/30.
 */
public enum  TaskPropressEnum {

    CONTINUE(1, "继续爬取"),
    SAVEDB(2,"保存数据库继续爬取"),
    TASKSTOP(3, "任务结束");

    private final Integer value;
    public String getName() {
        return name;
    }

    private final String name;

    TaskPropressEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public static TaskPropressEnum getEnumByCode(String code) {
        for (TaskPropressEnum enumInfo : TaskPropressEnum.values()) {
            if (enumInfo.value.equals(code)) {
                return enumInfo;
            }
        }

        return null;
    }

    public static final String Name = "RuleProcessEnum";

    public static DataSource toDataSource() {
        DataSource ds = new DataSource();
        for (TaskPropressEnum item : TaskPropressEnum.values()) {
            ds.getList().add(new DataSourceOption(item.getName(), item.getValue()));
            ds.getMap().put(item.getValue(), item.getName());
        }
        ds.init(Name);
        return ds;
    }

}
