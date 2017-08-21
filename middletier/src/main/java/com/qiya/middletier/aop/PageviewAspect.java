package com.qiya.middletier.aop;

import java.lang.reflect.Method;
import java.util.Map;

import com.qiya.framework.coreservice.RedisService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qiya.framework.baselib.web.WebUtils;
import com.qiya.framework.middletier.service.PageViewService;
import com.qiya.framework.model.ApiOutput;
import org.springframework.util.StringUtils;

/**
 * Created by qiyamac on 16/7/6.
 */
@Aspect
@Component
public class PageviewAspect {

	@Value("${system.name:none}")
	private String systemName;

	@Autowired
	RedisService redisService;

	@AfterReturning(value = "@annotation(com.qiya.middletier.aop.PageViewAnnotation)", returning = "returnValue")
	public void around(JoinPoint point, ApiOutput returnValue) throws Throwable {
		Signature signature = point.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		String json = point.getArgs()[0].toString();
		Map map = WebUtils.getJsonParams(json);
		PageViewAnnotation pageViewAnnotation = method.getAnnotation(PageViewAnnotation.class);
		String value = map.get(pageViewAnnotation.prarmkey()).toString()+"|"+pageViewAnnotation.value().code;
		if (returnValue.getData() != null) {

			String key = systemName+"|pageview|"+value;
			String clickNum = redisService.get(key);
			if(StringUtils.isEmpty(clickNum))
			{
				clickNum = "0";


			}
			redisService.setOrUpdate(key,String.valueOf(Long.parseLong(clickNum)+1),3600L);
		}
	}

}
