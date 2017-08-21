package com.qiya.middletier.bizenum;

/**
 * Created by qiyalm on 16/7/11.
 */
public enum  ConfigTypeEnum {

    MESS(1L, "非常满意"),
    feel_good(2L, "满意"),
    feel_bad(3L,"不满意");

    public Long id;
    public String name;

    ConfigTypeEnum(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public static ConfigTypeEnum getEnumById(Long id)
    {
        for(ConfigTypeEnum enumInfo: ConfigTypeEnum.values())
        {
            if(enumInfo.id.equals(id))
            {
                return enumInfo;
            }
        }
        return null;
    }

}
