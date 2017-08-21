package com.qiya.middletier.token.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.qiya.middletier.dao.UserDao;
import com.qiya.middletier.model.User;
import com.qiya.middletier.token.annotation.CurrentUser;

/**
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private UserDao userDao;

	@Value("${token.user.id.name:}")
	private String tokenUserIdName;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 如果参数类型是User并且有CurrentUser注解则支持
		if (parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(CurrentUser.class)) {
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// 取出鉴权时存入的登录用户Id
		Long currentUserId = (Long) webRequest.getAttribute(tokenUserIdName, RequestAttributes.SCOPE_REQUEST);
		if (currentUserId != null) {
			// 从数据库中查询并返回
			return userDao.findOne(currentUserId);
		}
		return null;
		// throw new MissingServletRequestPartException(tokenUserIdName);
	}
}
