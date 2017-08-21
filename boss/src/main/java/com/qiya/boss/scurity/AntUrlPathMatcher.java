package com.qiya.boss.scurity;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

/**
 * Created by qiyalm on 16/6/16.
 */
@Component
public class AntUrlPathMatcher implements IUrlMather {
    private boolean requiresLowerCaseUrl;
    private PathMatcher pathMatcher;
    public AntUrlPathMatcher() {
        this(true);
    }

    public AntUrlPathMatcher(boolean requiresLowerCaseUrl){
        this.requiresLowerCaseUrl = true;
        this.pathMatcher = new AntPathMatcher();
        this.requiresLowerCaseUrl = requiresLowerCaseUrl;
    }

    public Object compile(String path) {
        if (this.requiresLowerCaseUrl) {
            return path.toLowerCase();
        }
        return path;
    }

    public void setRequiresLowerCaseUrl(boolean requiresLowerCaseUrl) {
        this.requiresLowerCaseUrl = requiresLowerCaseUrl;
    }

    public boolean pathMatchesUrl(Object path, String url) {
        if (("/**".equals(path)) || ("**".equals(path))) {
            return true;
        }
        String path1=(String)path;
        if(StringUtils.isEmpty(url)){
            return  false;
        }
        if (path1.indexOf("/"+url)==0){
            return true;
        }else {
            return false;
        }

//        return this.pathMatcher.match((String)path, url);
    }

    public String getUniversalMatchPattern() {
        return"/**";
    }

    public boolean requiresLowerCaseUrl() {
        return this.requiresLowerCaseUrl;
    }

    public String toString() {
        return super.getClass().getName() + "[requiresLowerCase='" + this.requiresLowerCaseUrl + "']";
    }
}
