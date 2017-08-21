package com.qiya.middletier.webmagic.process;

import com.qiya.middletier.webmagic.comm.configmodel.RuleMatchConfig;
import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;

/**
 * Created by qiyalm on 17/3/27.
 */
@Service
@Scope("prototype")
public class XPathWebPageProcess extends AbstractCommPageProcess {

    private RuleMatchConfig ruleMatchConfig;

    @Override
    public void init(WebmagicConfig config) {
        super.setConfig(config);
    }

    @Override
    public void process(Page page) {
        this.ruleMatchConfig = super.getConfig().getRule();

        // 列表页
        if (!page.getUrl().regex(ruleMatchConfig.getDetailregex()).match()) {
            System.err.println(page.getHtml().xpath(ruleMatchConfig.getListxpath()).links().regex(ruleMatchConfig.getDetailregex()).all());
            page.addTargetRequests(page.getHtml().xpath(ruleMatchConfig.getListxpath()).links().regex(ruleMatchConfig.getDetailregex()).all());

            //爬取更多
            if (ruleMatchConfig.getLoadlistxpath() != null){
                System.err.println(page.getHtml().xpath(ruleMatchConfig.getLoadlistxpath()).links().regex(ruleMatchConfig.getListregex()).all());
                page.addTargetRequests(page.getHtml().xpath(ruleMatchConfig.getLoadlistxpath()).links().regex(ruleMatchConfig.getListregex()).all());
            }

            page.getResultItems().setSkip(true);
        } else {
            // 文章页
            this.setPutFile(page,ruleMatchConfig);
        }
        setIsCircle(page);
    }
}
