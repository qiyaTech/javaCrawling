package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

/**
 * Created by qiyalm on 17/3/28.
 */
public enum  TaskRunStateEnum {
    TASK_RUNNING("running", "任务运行中"),
    TASK_STOP("stop", "任务结束");

    private final String code;
    public String getName() {
        return name;
    }

    private final String name;

    TaskRunStateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public static TaskRunStateEnum getEnumByCode(String code) {
        for (TaskRunStateEnum enumInfo : TaskRunStateEnum.values()) {
            if (enumInfo.code.equals(code)) {
                return enumInfo;
            }
        }

        return null;
    }

    public static final String Name = "RuleProcessEnum";

    public static DataSource toDataSource() {
        DataSource ds = new DataSource();
        for (TaskRunStateEnum item : TaskRunStateEnum.values()) {
            ds.getList().add(new DataSourceOption(item.getName(), item.getCode()));
            ds.getMap().put(item.getCode(), item.getName());
        }
        ds.init(Name);
        return ds;
    }
}
