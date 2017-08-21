package com.qiya.middletier.webmagic.pipe;

import com.qiya.framework.coreservice.RedisService;
import com.qiya.middletier.bizenum.SpiderExceptionEnum;
import com.qiya.middletier.bizenum.TaskPropressEnum;
import com.qiya.middletier.bizenum.TaskRunStateEnum;
import com.qiya.middletier.service.MailSendService;
import com.qiya.middletier.webmagic.comm.configmodel.SpiderExceptionMsg;
import com.qiya.middletier.webmagic.monitor.MySpiderMonitor;
import com.qiya.middletier.webmagic.monitor.MySpiderStatus;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qiya.framework.baselib.mail.MailSender;
import com.qiya.framework.baselib.mail.MailSenderInfo;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.Map;

/**
 * Created by dengduiyi on 2017/1/7.
 */
@Service
@Scope("prototype")
public class ExceptionPipeline implements Pipeline {

    private static Logger log = LoggerFactory.getLogger(ExceptionPipeline.class);


    @Value("${spring.mail.sendTo:}")
    private String mailto;

    @Autowired
    RedisService redisService;

    @Value("${system.name:none}")
    private String systemName;

    @Value("${SpiderException.limit:50}")
    private Integer exceptionLimit;


    @Autowired
    private MailSendService mailSendService;


    @Override
    public void process(ResultItems resultItems, Task task) {
        try {


            Map<String,Object> data = resultItems.getAll();
            if (data.size()==0){
                resultItems.setSkip(true);
                return;
            }

            //判断时间是否在开始时间和结束时间内
            String key=systemName+SpiderExceptionEnum.Name+resultItems.getRequest().getUrl();
            if (data.get(SpiderExceptionEnum.Name) != null){
                SpiderExceptionMsg spiderException = (SpiderExceptionMsg)data.get(SpiderExceptionEnum.Name);

                if (spiderException.getType() == SpiderExceptionEnum.EXIT.getValue()){
                    //结束爬取任务
                    log.info("任务异常需要退出！");

                    MySpiderMonitor spiderMonitor = MySpiderMonitor.instance();
                    Map<String,MySpiderStatus> spiderStatuses = spiderMonitor.getSpiderStatuses();
                    MySpiderStatus spiderStatus = spiderStatuses.get(task.getUUID());

                    spiderStatus.stop();
                    spiderStatus.getSpider().close();

                    //修改任务状态
                    return;

                }else if (spiderException.getType().intValue() == SpiderExceptionEnum.NOTICE.getValue()){
                    log.info("任务异常进行通知！");
                    mailSendService.sendTextMail(spiderException.getTitle(),"\n"+task.getUUID()+"爬取任务异常："+spiderException.getMsg(),new String[]{mailto});
                    resultItems.setSkip(true);
                    return;
                }else if (spiderException.getType().intValue() == SpiderExceptionEnum.LIMITNOTICE.getValue()) {
                    //log.info("任务阀值异常进行通知！");


                    String num= redisService.get(key);
                   int limit=0;
                    if(num!=null){
                       limit= Integer.valueOf(num);
                    }
                    limit++;
                    log.info("任务阀值异常进行通知！当前"+limit+"次！");
                    if(limit<exceptionLimit) {
                       // redisService.del(key);
                        redisService.setOrUpdate(key, String.valueOf(limit),600l);
                    }else {
                        redisService.del(key);
                        mailSendService.sendTextMail(spiderException.getTitle(),   task.getUUID() + "爬取任务异常：" + spiderException.getMsg()
                                +"\n 已达到"+limit+"次，请注意查看！" , new String[]{mailto});

                    }

                   resultItems.setSkip(true);
                    return;
                }

            }else {
                redisService.del(key);
            }
        }
        catch (Exception ex)
        {
            log.error("异常处理出错:"+ex.getMessage(),ex);
        }
    }
}
