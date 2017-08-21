package com.qiya.middletier.webmagic;

import java.util.ArrayList;
import java.util.List;

import com.qiya.framework.baselib.util.base.LogUtils;
import com.qiya.framework.middletier.model.Config;
import com.qiya.framework.middletier.service.BizConfigService;
import com.qiya.framework.middletier.service.ConfigService;
import com.qiya.middletier.bizenum.BizConfigTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.qiya.framework.baselib.util.generate.RandomUtil;
import com.qiya.framework.coreservice.RedisService;
import com.qiya.middletier.bizenum.TaskStatusEnum;
import com.qiya.middletier.model.Task;
import com.qiya.middletier.service.TaskService;
import com.qiya.middletier.webmagic.comm.configmodel.RinseRule;
import com.qiya.middletier.webmagic.comm.configmodel.SpiderConfig;
import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;
import com.qiya.middletier.webmagic.downloader.CustomAbstractDownloader;
import com.qiya.middletier.webmagic.monitor.MySpiderMonitor;
import com.qiya.middletier.webmagic.process.AbstractCommPageProcess;
import com.qiya.middletier.webmagic.scheduler.RedisQuenUniqPriorityScheduler;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by qiyamac on 2017/4/17.
 */
@Service
public class WebmagicService {

	private static Logger log = LoggerFactory.getLogger(WebmagicService.class);

	@Autowired
	private RedisService redisService;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private BizConfigService configService;

	@Autowired
	TaskService taskService;

	public void circleTask() {
		List<Task> tasks = taskService.getTaskTimerTasks(TaskStatusEnum.TRUE.getValue());
		for (Task t : tasks) {
			Task task = new Task();
			BeanUtils.copyProperties(t, task);
			String json = task.getTaskRuleJson();
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				WebmagicConfig webmagicConfig = objectMapper.readValue(json, WebmagicConfig.class);
				webmagicConfig.setIsCircle(false);
				task.setSpiderUUID("timerTask-" + t.getTaskName() + "-" + RandomUtil.getUUID());
				run(webmagicConfig, task, false);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Async
	public void asynCircleTask() {
		circleTask();
	}

	public void run(WebmagicConfig webmagicConfig, Task task) throws Exception {
		run(webmagicConfig, task, true);
	}

	public void run(WebmagicConfig webmagicConfig, Task task, boolean runAsync) throws Exception {
		SpiderConfig spiderConfig = webmagicConfig.getSpider();
		MySpiderMonitor spiderMonitor = MySpiderMonitor.instance();

		AbstractCommPageProcess xPathProjectBasic = (AbstractCommPageProcess) context.getBean(spiderConfig.getProcesser());

		xPathProjectBasic.init(webmagicConfig);
		xPathProjectBasic.setUuid(task.getSpiderUUID());
		xPathProjectBasic.cleanStrurlrunKey();

		try {
			//设置通用规则
			List<Config>  configs =configService.getByCode(BizConfigTypeEnum.COMMRISE.getCode());
			if(configs!=null&&configs.size()>0){
				String json = configs.get(0).getValue();
				ObjectMapper objectMapper = new ObjectMapper();
				CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, RinseRule.class);
				List<RinseRule> rinseRules = objectMapper.readValue(json, listType);
				xPathProjectBasic.setCommRinseRules(rinseRules);
			}

		}catch (Exception e){
			log.error("通用模版转换错误：",e);
		}


		Spider spider = Spider.create(xPathProjectBasic).thread(spiderConfig.getThread());
		spider.setUUID(task.getSpiderUUID());

		List<String> pipeLines = spiderConfig.getPipeline();

		for (int i = 0; i < pipeLines.size(); i++) {
			if (context.getBean(pipeLines.get(i)) != null) {
				spider.addPipeline((Pipeline) context.getBean(pipeLines.get(i)));
			}
		}
		spider.setScheduler(new RedisQuenUniqPriorityScheduler(redisService.getPool()).resetDuplicateCheck(task.getSpiderUUID()));

		if (spiderConfig.getDownloader() != null && spiderConfig.getDownloader().length() > 0) {
			CustomAbstractDownloader customAbstractDownloader=  (CustomAbstractDownloader) context.getBean(spiderConfig.getDownloader());
			customAbstractDownloader.setConfig(webmagicConfig);
			spider.setDownloader(customAbstractDownloader);
		}else {
			Boolean isdynamicProxy = webmagicConfig.getSite().getDynamicProxy();
			if(isdynamicProxy !=null && isdynamicProxy.booleanValue()){
				CustomAbstractDownloader customAbstractDownloader=  (CustomAbstractDownloader) context.getBean("dynamicProxyHttpClientDownloader");
				customAbstractDownloader.setConfig(webmagicConfig);
				spider.setDownloader(customAbstractDownloader);
			}
		}

		spiderMonitor.register(spider);
		spider.addUrl(spiderConfig.getStartUrl());
		if (runAsync) {
			spider.runAsync();
		} else {
			spider.run();
		}

	}
}
