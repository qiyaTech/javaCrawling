package com.qiya.boss.controller;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.qiya.boss.menu.annotation.Menu;
import com.qiya.framework.baselib.util.generate.RandomUtil;
import com.qiya.framework.baselib.web.WebUtils;
import com.qiya.framework.coreservice.RedisService;
import com.qiya.framework.def.ConstDef;
import com.qiya.framework.model.ApiOutput;
import com.qiya.framework.model.SearchCondition;
import com.qiya.middletier.bizenum.TaskRunStateEnum;
import com.qiya.middletier.model.Task;
import com.qiya.middletier.model.TaskResult;
import com.qiya.middletier.service.ArticleService;
import com.qiya.middletier.service.MailSendService;
import com.qiya.middletier.service.TaskService;
import com.qiya.middletier.webmagic.RinseService;
import com.qiya.middletier.webmagic.WebmagicService;
import com.qiya.middletier.webmagic.comm.configmodel.DetailxpathConfig;
import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;
import com.qiya.middletier.webmagic.monitor.MySpiderMonitor;
import com.qiya.middletier.webmagic.monitor.MySpiderStatus;

/**
 * Created by qiyalm on 17/3/28.
 */
@Controller

@RequestMapping("/taskManager")
public class ArticleManagerController {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private WebmagicService webmagicService;

	@Autowired
	private MailSendService mailSendService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private RinseService rinseService;

	@Value("${spring.mail.sendTo:}")
	private String mailto;

	@Value("${wecharTaskid:}")
	private String wechartaskid;

	@Menu
	@RequestMapping(value = "")
	public ModelAndView runmanager(ModelAndView model) {
		model.setViewName("runmanager/taskRunManager");
		return model;
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public ApiOutput getList(Model model, @RequestBody SearchCondition sc) throws Exception {

		Page<TaskResult> taskResults = taskService.runSearch(sc);
		return new ApiOutput(taskResults);
	}

	@RequestMapping(value = "/start", produces = { ConstDef.ApiProduces })
	@ResponseBody
	public ApiOutput run(@RequestBody String taskId) throws Exception {

		Task task = taskService.read(taskId);
		if (task == null) {
			return new ApiOutput();
		}

		String json = task.getTaskRuleJson();

		ObjectMapper objectMapper = new ObjectMapper();
		WebmagicConfig webmagicConfig = objectMapper.readValue(json, WebmagicConfig.class);
		webmagicService.run(webmagicConfig, task);
		// 更新任务
		task.setRunState(TaskRunStateEnum.TASK_RUNNING.getCode());
		task.setStartTime(new Date());

		task = taskService.update(task);

		Map<String, Object> data = new HashMap<>();
		data.put("task", task);

		return new ApiOutput(data);
	}

	@RequestMapping(value = "/circleStart")
	@ResponseBody
	public ApiOutput circleTask() throws Exception {
		webmagicService.asynCircleTask();
		return new ApiOutput();
	}

	@RequestMapping(value = "/MsgStart", produces = { ConstDef.ApiProduces })
	@ResponseBody
	public ApiOutput MsgStart(@RequestBody String json) throws Exception {
		Map map = WebUtils.getJsonParams(json);
		Map rmao = Maps.newHashMap();
		String urlStr = map.get("text").toString();
		boolean isWeixin = false;
		String domain = "";

		if (urlStr.indexOf("mp.weixin.qq.com") >= 0) {
			isWeixin = true;
		}
		try {
			URL url = new URL(urlStr);
			domain = url.getHost();
		} catch (Exception e) {
			map.put("results", false);
			return new ApiOutput(map);
		}
		Task oldtask = null;
		// 获取模版
		if (isWeixin) {
			oldtask = taskService.read(wechartaskid);
		} else {
			oldtask = taskService.getTaskByDomain(domain);
		}

		Task task = new Task();
		if (oldtask == null) {
			mailSendService.sendTextMail("微信文章转发爬取无对应模版", urlStr + "无对应模版抓取", mailto);
			map.put("results", false);
			return new ApiOutput(map);
		} else {
			BeanUtils.copyProperties(oldtask, task);
			task.setSpiderUUID("微信转发任务爬取：" + RandomUtil.getUUID());
		}

		String rulejson = task.getTaskRuleJson();
		ObjectMapper objectMapper = new ObjectMapper();
		WebmagicConfig webmagicConfig = objectMapper.readValue(rulejson, WebmagicConfig.class);
		webmagicConfig.setIsCircle(false);
		webmagicConfig.getSpider().setStartUrl(urlStr);
		if (webmagicConfig.getCondition() != null) {
			webmagicConfig.getCondition().clear();
		}
		if (isWeixin) {
			webmagicConfig.getRule().setDetailregex("(http|ftp|https)://mp\\.weixin\\.qq\\.com/s\\S+");
			webmagicConfig.getWechat().setUrl(urlStr);

			webmagicConfig.getRule().getDetailxpath().add(new DetailxpathConfig("weicahtName", "//*[@id=\"js_profile_qrcode\"]/div/strong/text()"));
			webmagicConfig.getRule().getDetailxpath().add(new DetailxpathConfig("weicahtNo", "//*[@id=\"js_profile_qrcode\"]/div/p[1]/span/text()"));
			webmagicConfig.getRule().getDetailxpath().add(new DetailxpathConfig("weicahtNo2", "var user_name = \"(.*?)\";", "//*[@id=\"activity-detail\"]/script[5]"));
		}
		List<String> pipelineLis = webmagicConfig.getSpider().getPipeline();
		for (int i = 0; i < pipelineLis.size(); i++) {
			String pipeline = pipelineLis.get(i);
			if ("mySqlArticlePipeline".equals(pipeline)) {
				pipelineLis.remove(i);
				break;
			}
		}
		pipelineLis.add("weChatMsgMySqlArticlePipeline");
		webmagicService.run(webmagicConfig, task);
		map.put("results", true);
		return new ApiOutput(map);
	}

	@RequestMapping(value = "/stop", produces = { ConstDef.ApiProduces })
	@ResponseBody
	public ApiOutput stop(@RequestBody String taskId) throws Exception {

		Task task = taskService.read(taskId);

		MySpiderMonitor spiderMonitor = MySpiderMonitor.instance();
		Map<String, MySpiderStatus> spiderStatuses = spiderMonitor.getSpiderStatuses();
		MySpiderStatus spiderStatus = spiderStatuses.get(task.getSpiderUUID());

		if (spiderStatus != null) {
			spiderStatus.stop();
			spiderStatus.getSpider().close();
		}

		// 修改任务状态
		task.setEndTime(new Date());
		task.setRunState(TaskRunStateEnum.TASK_STOP.getCode());
		task = taskService.update(task);

		MySpiderStatus spiderStatus1 = spiderStatuses.get(task.getSpiderUUID());

		Map<String, Object> data = new HashMap<>();
		if (task == null) {
			data.put("success", 0);
		} else {
			data.put("success", 1);
		}

		return new ApiOutput(data);
	}

	@RequestMapping(value = "/articlelist", produces = { ConstDef.ApiProduces })
	@ResponseBody
	public ApiOutput articlelist(@RequestBody SearchCondition sc) throws Exception {
		return new ApiOutput(articleService.searchBySite(sc));
	}

	@RequestMapping(value={"/rinseById/{taskid}/{artliIdtList}"}, method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
	@ResponseBody
	public ApiOutput rinseById( @PathVariable("artliIdtList") List<String> artliIdtList,@PathVariable("taskid") String taskid){
		rinseService.rinseByartliIdtList(artliIdtList,taskid);
		return new ApiOutput();
	}

	@RequestMapping(value={"/rinseBySearchCondition/{taskid}"}, method = {RequestMethod.GET, RequestMethod.POST},produces="application/json")
	@ResponseBody
	public ApiOutput rinseBySearchCondition(@RequestBody SearchCondition sc,@PathVariable("taskid") String taskid){
		rinseService.rinseBySearchCondition(sc,taskid);
		return new ApiOutput();
	}
}
