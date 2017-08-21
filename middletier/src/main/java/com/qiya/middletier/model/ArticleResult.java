package com.qiya.middletier.model;
import org.springframework.beans.BeanUtils;
import com.qiya.framework.baselib.util.base.DateUtil;
public class ArticleResult extends Article {
    private String siteName;
    private String createTimeStr;
    public static ArticleResult toSiteArticleResult(Article source, Site site) {
        ArticleResult result = new ArticleResult();
        BeanUtils.copyProperties(source, result);
        result.setCreateTimeStr(DateUtil.dateTimeToString(source.getCreateTime()));
        result.setSiteName(site.getSiteNick());
        return result;
    }
    public String getSiteName() {
        return siteName;
    }
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
    public String getCreateTimeStr() {
        return createTimeStr;
    }
    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}