package com.qiya.middletier.model;

import org.springframework.beans.BeanUtils;

/**
 * Created by qiyalm on 17/3/31.
 */
public class SiteResult extends Site {

    private String typeName;

    public static SiteResult toSiteResult(Site source,String typeName) {
        SiteResult result = new SiteResult();
        BeanUtils.copyProperties(source, result);
        result.setTypeName(typeName);
        return result;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
