package com.qiya.middletier.webmagic.process;

import java.net.URL;
import java.util.List;

import com.qiya.framework.baselib.util.generate.GenerateUtils;
import com.qiya.framework.coreservice.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qiya.middletier.webmagic.comm.configmodel.RuleMatchConfig;
import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;

import redis.clients.jedis.exceptions.JedisException;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.UrlUtils;

/**
 * Created by qiyalm on 17/3/27.
 */
@Service
@Scope("prototype")
public class XPathWebPageListImageProcess extends AbstractCommPageProcess {

    private static Logger log = LoggerFactory.getLogger(XPathWebPageListImageProcess.class);

    private RuleMatchConfig ruleMatchConfig;

    @Autowired
    RedisService redisService;
    @Value("${system.name:none}")
    private String systemName;
    final private String PREFIX = "ARTIC_LIST_IMAGE";
    final private String SPLIT = "|";
    final private Long EXPIRE = 2*60 * 60L;

    @Override
    public void init(WebmagicConfig config) {
        super.setConfig(config);
    }

    @Override
    public void process(Page page) {
        this.ruleMatchConfig = super.getConfig().getRule();

        // 列表页
        if (!page.getUrl().regex(ruleMatchConfig.getDetailregex()).match()) {

            List<Selectable> itemSelectableList = page.getHtml().xpath(ruleMatchConfig.getItemxpath()).nodes();

            for(int i=0;i<itemSelectableList.size();i++)
            {

                String  url = itemSelectableList.get(i)
                        .xpath(ruleMatchConfig.getListxpath()).links()
                        .regex(ruleMatchConfig.getDetailregex()).get();

                String imageAddress = redisService.get(systemName+this.SPLIT+this.PREFIX+this.SPLIT+url);

                if(StringUtils.isEmpty(url))
                {
                    continue;
                }

                if(StringUtils.isEmpty(imageAddress))
                {
                    String image = itemSelectableList.get(i)
                            .xpath(ruleMatchConfig.getItemImagexpath()).get();
                    if(!StringUtils.isEmpty(image))
                    {
                        image = UrlUtils.canonicalizeUrl(image,page.getUrl().toString());
                        redisService.set(systemName+this.SPLIT+this.PREFIX+this.SPLIT+url,image,EXPIRE);

                        log.info("set存储图片列表:url{},img:{}",url,image);
                    }
                }


                page.addTargetRequest(url);

            }

            //爬取更多
            if (ruleMatchConfig.getLoadlistxpath() != null){
                page.addTargetRequests(page.getHtml().xpath(ruleMatchConfig.getLoadlistxpath()).links().regex(ruleMatchConfig.getListregex()).all());
            }

            page.getResultItems().setSkip(true);
        } else {
            // 文章页
            this.setPutFile(page,ruleMatchConfig);
            try {
                String url = redisService.get(systemName+this.SPLIT+this.PREFIX+this.SPLIT
                        +page.getRequest().getUrl());
                log.info("get存储图片列表:url{},img:{}",page.getRequest().getUrl(),url);
                if(!StringUtils.isEmpty(url))
                {
                    page.putField("pic", url);
                }
            }catch (JedisException e){
                log.error("获取jedis错误",e);
                log.error("getNumActive:" +redisService.getPool().getNumActive());
                log.error("getNumWaiters:" + redisService.getPool().getNumWaiters());
                log.error("getNumIdle:" + redisService.getPool().getNumIdle());
                throw e;
            }

        }
        setIsCircle(page);
    }
}
