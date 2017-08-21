package com.qiya.middletier.token.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.qiya.framework.exception.CustomBizException;
import com.qiya.middletier.token.annotation.Authorization;
import com.qiya.middletier.token.manager.ITokenManager;
import com.qiya.middletier.token.model.TokenModel;
import com.qiya.framework.model.ApiCodeEnum;

/**
 * 自定义拦截器，判断此次请求是否有权限
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(AuthorizationInterceptor.class);

	@Value("${appid.name:}")
	private String appidName;

	@Value("${token.name:}")
	private String tokenName;

	@Value("${token.user.id.name:}")
	private String tokenUserIdName;

	@Autowired
	private ITokenManager manager;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		// 从header中得到token
		String token = request.getHeader(tokenName);
		String appid = request.getHeader(appidName);

		log.info("token:{},appid:{}",token,appid);
		
		// 验证token
		if (manager.checkToken(token)) {

			TokenModel model = manager.getToken(token);
			// 如果token验证成功，将token对应的用户id存在request中，便于之后注入
			request.setAttribute(tokenUserIdName, model.getUserId());
			return true;
		}

		// 如果验证token失败，并且方法注明了Authorization，返回401错误
		if (method.getAnnotation(Authorization.class) != null) {
			throw new CustomBizException(ApiCodeEnum.NEED_LOGIN, "请先登录");
			// response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			// return false;
		}

		return true;
	}
}
