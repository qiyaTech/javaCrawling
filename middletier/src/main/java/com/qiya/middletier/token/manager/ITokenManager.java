package com.qiya.middletier.token.manager;

import com.qiya.middletier.token.model.TokenModel;

/**
 * 对Token进行操作的接口
 */
public interface ITokenManager {

	/**
	 * 创建一个token关联上指定用户
	 * 
	 * @param userId 指定用户的id
	 * @return 生成的token
	 */
	public TokenModel createToken(long userId);

	/**
	 * 检查token是否有效
	 * 
	 * @param model token
	 * @return 是否有效
	 */
	public boolean checkToken(TokenModel model);

	/**
	 * 检查token是否有效
	 * 
	 * @param token
	 * @return
	 */
	public boolean checkToken(String token);

	/**
	 * 从字符串中解析token
	 * 
	 * @param token
	 * @return
	 */
	public TokenModel getToken(String token);

	/**
	 * 清除token
	 * 
	 * @param token 登录用户的token
	 */
	public void deleteToken(String token);

}
