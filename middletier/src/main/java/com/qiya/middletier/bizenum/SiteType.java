package com.qiya.middletier.bizenum;

import com.qiya.framework.model.DataSource;
import com.qiya.framework.model.DataSourceOption;

/**
 * Created by qiyalm on 17/3/31.
 */
public enum  SiteType {

    WECHAT(1, "微信公众号"),
    WEB(2, "网站");

    public Integer id;
    public String name;

    SiteType(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public static SiteType getEnumById(Integer id)
    {
        for(SiteType enumInfo: SiteType.values())
        {
            if(enumInfo.id.equals(id))
            {
                return enumInfo;
            }
        }
        return null;
    }

    public static final String Name = "SiteType";

    public static final String CodeName = "SiteType";

    public static DataSource toDataSource() {
        DataSource ds = new DataSource();
        for (SiteType item : SiteType.values()) {
            ds.getList().add(new DataSourceOption(item.getName(), item.getId()));
            ds.getMap().put(item.getId(), item.getName());
        }
        ds.init(Name);
        return ds;
    }

    public static DataSource toCodeDataSource() {
        DataSource ds = new DataSource();
        for (SiteType item : SiteType.values()) {
            ds.getList().add(new DataSourceOption(item.getName(), item.getId()));
            ds.getMap().put(item.getId(), item.getName());
        }
        ds.init(CodeName);
        return ds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
