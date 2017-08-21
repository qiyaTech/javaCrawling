package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

/**
 * Created by qiyalm on 17/3/30.
 */
public enum TaskStatusEnum {

    TRUE (1, "是"),
    FALSE (2,"否");

    private final Integer value;
    public String getName() {
        return name;
    }

    private final String name;

    TaskStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public static TaskStatusEnum getEnumByValue(Integer value) {
        for (TaskStatusEnum enumInfo : TaskStatusEnum.values()) {
            if (enumInfo.value.equals(value)) {
                return enumInfo;
            }
        }

        return null;
    }

    public static final String Name = "taskStatusEnum";

    public static DataSource toDataSource() {
        DataSource ds = new DataSource();
        for (TaskStatusEnum item : TaskStatusEnum.values()) {
            ds.getList().add(new DataSourceOption(item.getName(), item.getValue()));
            ds.getMap().put(item.getValue(), item.getName());
        }
        ds.init(Name);
        return ds;
    }

}
