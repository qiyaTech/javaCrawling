package com.qiya.boss.menu.aop;


import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiya.framework.coreservice.RedisService;
import com.qiya.framework.middletier.model.ScurityUser;
import com.qiya.framework.middletier.service.ScurityRoleService;
import com.qiya.framework.middletier.service.ScurityUserRoleService;
import com.qiya.framework.middletier.service.ScurityUserService;


/**
 * Created by qiyamac on 16/7/6.
 */
@Aspect
@Component
public class MenuAspect {

	@Autowired
	private ScurityUserService userService;

	@Autowired
	private ScurityRoleService roleService;

	@Autowired
	private ScurityUserRoleService userRoleService;

	@Autowired
	private RedisService redisService;

	private String redisMenuKey = "MenuResource:";

	@Value("${Menu.hours:1}")
	private int hours;


	@AfterReturning(value = "@annotation(com.qiya.boss.menu.annotation.Menu)", returning = "modelAndView")
	public ModelAndView around(JoinPoint point, ModelAndView modelAndView) throws Throwable {
		ObjectMapper objectMapper=new ObjectMapper();
		String phone= SecurityContextHolder.getContext().getAuthentication().getName();
		List<Map<String,Object>> mapList=null;
		String res=  redisService.get(redisMenuKey+phone );
		String name= redisService.get(redisMenuKey+phone+"name" );
		if(res==null){
			ScurityUser scurityUser= userService.getUserInfo(phone);
			name=scurityUser.getNickName();
			List<Map<String,Object>> roleList=roleService.getRoleInfoByUser(scurityUser.getId());
			if (roleList.size()>0){
				mapList=userRoleService.getMenu(roleList);
			}
			redisService.setOrUpdate(redisMenuKey+phone+"name",scurityUser.getNickName(),hours*3600L);
			redisService.setOrUpdate(redisMenuKey+phone,objectMapper.writeValueAsString(mapList),hours*3600L);
		}else {
			mapList=objectMapper.readValue(res, new TypeReference<List<Map>>() {});
		}
		if(mapList==null || mapList.size()==0){
			modelAndView.setViewName("errorPage");
		}
		modelAndView.addObject("menu",mapList);
		modelAndView.addObject("adminName",name);
		modelAndView.addObject("adminPhone",phone);
 		return modelAndView;
	}

}
