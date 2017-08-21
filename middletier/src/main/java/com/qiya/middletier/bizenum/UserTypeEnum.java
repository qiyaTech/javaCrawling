package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

/**
 * Created by qiyamac on 16/11/2.
 */
public enum UserTypeEnum {

    USER("user", "普通用户"), MANAGE("manage", "管理员");
    private final String code;

    public String getName() {
        return name;
    }

    private final String name;

    UserTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public static UserTypeEnum getEnumByCode(String code) {
        for (UserTypeEnum enumInfo : UserTypeEnum.values()) {
            if (enumInfo.code.equals(code)) {
                return enumInfo;
            }
        }

        return null;
    }

    public static final String Name = "UserType";

    public static DataSource toDataSource() {
        DataSource ds = new DataSource();
        for (UserTypeEnum item : UserTypeEnum.values()) {
            ds.getList().add(new DataSourceOption(item.getName(), item.getCode()));
            ds.getMap().put(item.getCode(), item.getName());
        }
        ds.init(Name);
        return ds;
    }
}
