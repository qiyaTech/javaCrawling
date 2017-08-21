package com.qiya.boss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.LinkedBlockingQueue;

@ComponentScan({ "com.qiya.*", "com.baidu.*" })
@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
// @ImportResource({ "classpath:disconf.xml" }) // 引入disconf
public class BossApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BossApplication.class);
	}

	@Bean
	public ThreadPoolTaskExecutor createThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(10);
		threadPoolTaskExecutor.setMaxPoolSize(20);
		return threadPoolTaskExecutor;
	}

	@Bean(name = "userLookInfoQueue")
	public LinkedBlockingQueue<?> userLookInfoQueue() {
		return new LinkedBlockingQueue(1000);
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BossApplication.class);
		// app.addListeners(new ApplicationStartup());
		app.run(args);
	}

}
