package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

/**
 * Created by qiyalm on 17/1/6.
 */
public enum SuccessEnum {

    SUCCESS("成功", 1), FAIL("失败", 0),Working("进行中",2);

    private final String name;
    private final int value;

    SuccessEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static SuccessEnum parse(int value) {
        for (SuccessEnum item : SuccessEnum.values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return null;
    }

    public static final String Name = "Status";

    public static DataSource toDataSource() {
        DataSource ds = new DataSource();
        for (SuccessEnum item : SuccessEnum.values()) {
            ds.getList().add(new DataSourceOption(item.getName(), item.getValue()));
            ds.getMap().put(item.getValue(), item.getName());
        }
        ds.init(Name);
        return ds;
    }

}
