package com.qiya.boss.sys;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

@Configuration
public class WebMagicConfig {


	@Bean(name="consolePipeline")
	public ConsolePipeline consolePipeline()
	{
		ConsolePipeline consolePipeline = new ConsolePipeline();
		return consolePipeline;
	}
}