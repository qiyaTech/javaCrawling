package com.qiya.middletier.webmagic.process;

import com.qiya.middletier.bizenum.TaskPropressEnum;
import com.qiya.middletier.webmagic.comm.configmodel.RuleMatchConfig;
import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by qiyalm on 17/3/29.
 */
@Service
@Scope("prototype")
public class XPathWebMorePageProcess extends AbstractCommPageProcess{

    private RuleMatchConfig ruleMatchConfig;

    private int addPage;
    private int thisPage;

    @Override
    public void init(WebmagicConfig config) {
        super.setConfig(config);

        Map condition = config.getCondition();
        if (condition!=null){
            addPage = condition.get("addPage") == null ? 0 : Integer.parseInt(condition.get("addPage").toString());
            thisPage = condition.get("thisPage") == null ? 0 : Integer.parseInt(condition.get("thisPage").toString());
        }
    }

    @Override
    public void process(Page page) {
        this.ruleMatchConfig = super.getConfig().getRule();

        // 列表页
        if (!page.getUrl().regex(ruleMatchConfig.getDetailregex()).match()) {

            List<String> urls = page.getHtml().xpath(ruleMatchConfig.getListxpath()).links().regex(ruleMatchConfig.getDetailregex()).all();

//            System.err.println(page.getHtml().xpath(ruleMatchConfig.getListxpath()).links().regex(ruleMatchConfig.getDetailregex()).all());
            page.addTargetRequests(page.getHtml().xpath(ruleMatchConfig.getListxpath()).links().regex(ruleMatchConfig.getDetailregex()).all());

            if (addPage >0 && urls != null && urls.size()>0){
                String url = page.getUrl().toString().substring(0,page.getUrl().toString().lastIndexOf("=")+1);
                thisPage += addPage;
                url += thisPage;
                page.addTargetRequest(url);
            }

            page.getResultItems().setSkip(true);
        } else {
            // 文章页
            this.setPutFile(page,ruleMatchConfig);
        }
        setIsCircle(page);
    }
}
