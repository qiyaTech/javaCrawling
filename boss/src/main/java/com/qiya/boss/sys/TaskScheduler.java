package com.qiya.boss.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qiya.middletier.webmagic.WebmagicService;

@Component
@Configurable
@EnableScheduling
public class TaskScheduler {
	private static Logger log = LoggerFactory.getLogger(TaskScheduler.class);

	// 定时任务循环时间毫秒
	@Autowired
	WebmagicService webmagicService;

	@Scheduled(cron = "0 0 5,17 * * ? ")
	public void timerTasks() {
		webmagicService.circleTask();

	}

}