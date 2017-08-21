package com.qiya.middletier.bizenum;
/**
 * Created by qiyalm on 16/7/22.
 */
public enum  CmsCatergoryEnum {
    aritic("aritic", "文章", 1L),
    ;
    public final String code;
    public final String name;
    public final Long id;
    CmsCatergoryEnum(String code, String name, Long id) {
        this.code = code;
        this.name = name;
        this.id = id;
    }
    public static CmsCatergoryEnum getEnumByCode(String code) {
        for (CmsCatergoryEnum enumInfo : CmsCatergoryEnum.values()) {
            if (enumInfo.code.equals(code)) {
                return enumInfo;
            }
        }
        return null;
    }
}