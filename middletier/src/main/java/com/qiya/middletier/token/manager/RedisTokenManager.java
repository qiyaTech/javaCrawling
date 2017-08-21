package com.qiya.middletier.token.manager;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qiya.middletier.token.model.TokenModel;
import com.qiya.framework.coreservice.RedisService;

/**
 * 通过Redis存储和验证token的实现类
 */
@Component
public class RedisTokenManager implements ITokenManager {

	@Autowired
	RedisService redisService;

	@Value("${token.expires.hours:72}")
	private int hours;

	public TokenModel createToken(long userId) {
		// 使用uuid作为源token
		String token = UUID.randomUUID().toString().replace("-", "");
		TokenModel model = new TokenModel(userId, token);
		// 存储到redis并设置过期时间
		redisService.set(token, String.valueOf(userId), new Long(hours * 3600));
		return model;
	}

	public TokenModel getToken(String token) {
		if (token == null || token.length() == 0) {
			return null;
		}
		Long userId = (redisService.get(token) == null ? null : Long.parseLong(redisService.get(token)));
		return new TokenModel(userId, token);
	}

	public boolean checkToken(String token) {
		if (token == null || token.length() == 0) {
			return false;
		}
		Long userId = (redisService.get(token) == null ? null : Long.parseLong(redisService.get(token)));
		if (userId == null) {
			return false;
		}
		// 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
		redisService.expire(token, new Long(hours * 3600));
		return true;
	}

	public boolean checkToken(TokenModel model) {
		if (model == null) {
			return false;
		}
		Long userId = (redisService.get(model.getToken()) == null ? null : Long.parseLong(redisService.get(model.getToken())));
		if (userId == null || !(userId.compareTo(model.getUserId()) == 0)) {
			return false;
		}
		// 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
		redisService.expire(model.getToken(), new Long(hours * 3600));
		return true;
	}

	public void deleteToken(String token) {
		redisService.del(token);
	}
}
