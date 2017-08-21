package com.qiya.boss.scurity;

/**
 * Created by qiyalm on 16/6/16.
 */
public interface IUrlMather {

    public abstract Object compile(String paramString);

    public abstract boolean pathMatchesUrl(Object paramObject, String paramString);

    public abstract String getUniversalMatchPattern();

    public abstract boolean requiresLowerCaseUrl();

}
