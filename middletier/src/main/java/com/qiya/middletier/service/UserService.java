package com.qiya.middletier.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.qiya.framework.middletier.model.Config;
import com.qiya.framework.middletier.service.ConfigService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.qiya.middletier.bizenum.SourceFromEnum;
import com.qiya.middletier.dao.UserDao;
import com.qiya.framework.exception.CustomBizException;
import com.qiya.middletier.model.User;
import com.qiya.middletier.model.UserSource;
import com.qiya.framework.coreservice.weixin.WeixinService;
import com.qiya.framework.def.StatusEnum;
import com.qiya.framework.model.ApiCodeEnum;

/**
 * Created by qiyamac on 16/6/15.
 */
@Service
public class UserService {

	private static Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private SmsCodeService smsCodeService;

	@Autowired
	ConfigService configService;

	@Autowired
	private UserSourceService userSourceService;

	@Autowired
	private WeixinService weixinService;

	@Value("${qiniu.http.context:}")
	private String context;


	public Page<User> getUserByPage(int index,int size,String word){
		return userDao.getUserByPage(StatusEnum.VALID.getValue(),word,new PageRequest(index,size));
	}

	public int getUserCount(){
		return userDao.getAllUserCount(StatusEnum.VALID.getValue());
	}

	/**
	 * 登录
	 * 
	 * @param mobile
	 * @param password
	 * @return
	 */
	public User login(String mobile, String password) {
		User u = userDao.getUserByMobile(mobile);
		if (u == null) {
			throw new CustomBizException(ApiCodeEnum.LOGIN_FAIL, "没有找到用户名!");
		}
		if (!u.getPassword().equals(password)) {
			throw new CustomBizException(ApiCodeEnum.LOGIN_FAIL, "密码错误!");
		}
		return u;
	}

	/**
	 * 登录判断用户是否完善信息
	 *
	 * @param user
	 * @return
	 */
	public Boolean checkUserInfo(User user) {
		if (StringUtils.isEmpty(user.getName()) ){
			return false;
		}

		//todo
		return true;
	}

	/**
	 * 验证码登录
	 *
	 * @param mobile
	 * @param smsCode
	 * @return
	 */
	public User smsCodeLogin(String mobile, String smsCode) {
		if (!smsCodeService.isRightSmsCode(mobile, smsCode)) {
			throw new CustomBizException("手机验证码错误!!");
		}

		// 查询手机注册没,没有则添加用户
		User u = userDao.getUserByMobile(mobile);
		if (u == null) {
			User user = new User();
			user.setMobile(mobile);
			user.setCreatetime(new Date());
			user.setStatus(StatusEnum.VALID.getValue());
			user.setPassword("");
			u=userDao.save(user);
		}

		return u;
	}

	/**
	 * 注册
	 * 
	 * @param mobile
	 * @param password
	 * @param smsCode
	 * @return
	 */
	public User reg(String mobile, String password, String smsCode) {
		if (!smsCodeService.isRightSmsCode(mobile, smsCode)) {
			throw new CustomBizException("手机验证码错误!!");
		}
		// 查询手机注册没
		User u = userDao.getUserByMobile(mobile);
		if (u != null) {
			throw new CustomBizException("用户手机号" + mobile + "已经存在!");
		}
		User user = new User();
		user.setMobile(mobile);
		user.setHeadImage(context+"image/config/myhead.png");
		user.setPassword(password);
		user.setCreatetime(new Date());
		user.setStatus(StatusEnum.VALID.getValue());
		return userDao.save(user);
	}

	/**
	 * 修改密码
	 * 
	 * @param mobile
	 * @param password
	 * @param smsCode
	 * @return
	 */
	public User modifyPwd(String mobile, String password, String smsCode) {
		if (!smsCodeService.isRightSmsCode(mobile, smsCode)) {
			throw new CustomBizException("手机验证码错误!!");
		}
		// 查询手机注册没
		User u = userDao.getUserByMobile(mobile);
		if (u == null) {
			throw new CustomBizException("用户手机号" + mobile + "不存在!");
		}

		u.setPassword(password);
		userDao.updateUserPassword(password, mobile);

		return u;
	}

	/**
	 * 修改密码
	 * 
	 * @param mobile
	 * @param password
	 * @param smsCode
	 * @return
	 */
	public User forgetPwd(String mobile, String password, String smsCode) {
		if (!smsCodeService.isRightSmsCode(mobile, smsCode)) {
			throw new CustomBizException("手机验证码错误!!");
		}
		// 查询手机注册没
		User u = userDao.getUserByMobile(mobile);
		if (u == null) {
			throw new CustomBizException("用户手机号" + mobile + "不存在!");
		}

		userDao.updateUserPassword(password, mobile);
		u.setPassword(password);
		return u;
	}

	/**
	 * 获取个人信息
	 * 
	 * @param user
	 * @return
	 */
	public Map<String, Object> getPersonInfo(User user) {
		// 查询用户信息
		Map<String, Object> map = new HashMap<>();
		map.put("mobile", user.getMobile());
		map.put("userId", user.getId());
		map.put("name", user.getName());
		String headImage = user.getHeadImage();
		if (StringUtils.isNotEmpty(headImage)) {
			if (headImage.toUpperCase().indexOf("HTTP") > -1) {
				map.put("head_image", headImage);
			} else {
				map.put("head_image", context + headImage);
			}
		} else {
			map.put("head_image", null);
		}
		map.put("sex",user.getSex());

//		if (user.getDefaultCom() != null && user.getDefaultCom() != 0) {
//			map.put("default_com_name", communityService.getCommunoty(user.getDefaultCom()).getName());
//		} else {
//			map.put("default_com_name", null);
//		}
		map.put(SourceFromEnum.MOBLIEWEBWX.getCode(),false);
		map.put(SourceFromEnum.MOBLIEWEBQQ.getCode(),false);
		List<UserSource> userSourceList = userSourceService.getUserSource(user.getId());
		for (UserSource us:userSourceList){
			if(SourceFromEnum.MOBLIEWEBQQ.getCode().equals(us.getSourceFrom())){
				map.put(SourceFromEnum.MOBLIEWEBQQ.getCode(),true);
			}
			if(SourceFromEnum.MOBLIEWEBWX.getCode().equals(us.getSourceFrom())){
				map.put(SourceFromEnum.MOBLIEWEBWX.getCode(),true);
			}
		}
		if(user.getMobile()!=null&&!("".equals(user.getMobile()))){
			map.put(SourceFromEnum.MOBLIEWEB.getCode(),true);
		}else {
			map.put(SourceFromEnum.MOBLIEWEB.getCode(),false);
		}
		if (userSourceList.size() > 0) {
			map.put("open_id", userSourceList.get(0).getOpenId());
		} else {
			map.put("open_id", null);
		}
//		map.put("noCompletedTask",taskService.getNnComplete(user.getId()));
//		map.put("healthPoint",userHealthExpandService.getUserHealthExpandInfo(user.getId()).getHealthPoint());
//		map.put("un_read_msg", String.valueOf(userMessageService.countUserMessageB(user.getId(), 0)));
		// TODO: 16/6/16 常见问题url 先由静态提供
		map.put("question_url", null);

		return map;
	}

	/**
	 * 修改用户头像
	 * 
	 * @param userid
	 * @param head_image
	 * @return
	 */
	public User updatePersonInfo(long userid, String head_image) {
		//更新任务
//		userOneTimeTaskService.updateOneTimeTaskPiont("UpImage",userid);
		// 查询用户信息
		User u = userDao.findOne(userid);
		u.setHeadImage(head_image);
		return userDao.save(u);

	}

	public User findUser(String mobile) {
		User u = userDao.getUserByMobile(mobile);
		if (u == null) {
			throw new CustomBizException("用户手机号" + mobile + "不存在!");
		}
		return u;
	}

	// 根据来源查询用户
	public User oauthLogin(String openid, String sourcecode) {

		return userDao.getUserByOpenId(openid, sourcecode, StatusEnum.VALID.getValue());
	}
	// 根据来源查询用户
	public User thirdPartyOauthLogin(String openid, String sourcecode,String name ,String
			headimage) {
		User u=  userDao.getUserByOpenId(openid, sourcecode, StatusEnum.VALID.getValue());
		if(u==null){
			u = new User();
			u.setName(name);
			u.setHeadImage(headimage);
			u.setCreatetime(new Date());
			u.setCreatetime(new Date());
			u.setStatus(StatusEnum.VALID.getValue());
			u = userDao.save(u);
			// 保存来源
			UserSource userSource = new UserSource();
			userSource.setUserId(u.getId());
			userSource.setSourceFrom(sourcecode);
			userSource.setOpenId(openid);
			userSourceService.save(userSource);

		}
		return u;

	}


	public User checkGuid(User user,String openid) {
		if(user!=null){
			return  user;
		}
		User u=  userDao.getUserByOpenId(openid,SourceFromEnum.GUID.getCode(), StatusEnum.VALID.getValue());
//		if(u==null){
//			List<Config> configs= configService.getConfigByCode(Constants.USERINFO);
//			Map<String, String> userConfig = configs.stream().collect(
//					Collectors.toMap(Config::getName,Config::getValue));
//			u = new User();
//			u.setName(userConfig.get("userName")!=null?userConfig.get("userName"):"天才家长");
//			u.setHeadImage(userConfig.get("headImage")!=null?userConfig.get("headImage"):"http://pic.58pic.com/58pic/15/42/50/78B58PICmc7_1024.png");
//			u.setCreatetime(new Date());
//			u.setStatus(StatusEnum.VALID.getValue());
//			u = userDao.save(u);
//			// 保存来源
//			UserSource userSource = new UserSource();
//			userSource.setUserId(u.getId());
//			userSource.setSourceFrom(SourceFromEnum.GUID.getCode());
//			userSource.setOpenId(openid);
//			userSourceService.save(userSource);
//
//		}
		return u;

	}

	public UserSource bindthirdPartyOauth(String openid, String sourcecode,User user) {
		UserSource userSource=  userSourceService.getUserSource(openid,sourcecode);
		if(userSource!=null){
			 throw new CustomBizException("此 "+SourceFromEnum.getEnumByCode(sourcecode).getName()
					 +"已绑定用户!!");
		}else {
			 userSource = new UserSource();
			userSource.setUserId(user.getId());
			userSource.setSourceFrom(sourcecode);
			userSource.setOpenId(openid);
			userSourceService.save(userSource);
		}
		return userSource;

	}

	/**
	 * 完善用户信息
	 * 
	 * @param mobile 手机号码
	 * @param openId openid
	 * @param smsCode 验证码
	 * @param soucefrom 来源
	 * @param headimage 头像
	 * @param name 名称
	 * @return
	 */
	public User perfectUser(String mobile, String openId, String smsCode, String soucefrom, String headimage, String name) {
		if (!smsCodeService.isRightSmsCode(mobile, smsCode)) {
			throw new CustomBizException("手机验证码错误!!");
		}
		// 查询手机注册没
		User user = userDao.getUserByMobile(mobile);
		if (user != null) {// 手机号码已经存在
			// 直接保存来源
			UserSource userSource = new UserSource();
			userSource.setUserId(user.getId());
			userSource.setSourceFrom(soucefrom);
			userSource.setOpenId(openId);
			userSourceService.save(userSource);
			log.info(SourceFromEnum.getEnumByCode(soucefrom).getName() + "用户OpenID:" + openId + "更新到手机号码为:" + mobile + "的用户");

		} else {// 手机号码不存在
				// 新建用户保存
			user = new User();
			user.setName(name);
			user.setHeadImage(headimage);
			user.setMobile(mobile);
			user.setPassword("");
			user.setCreatetime(new Date());
			user = userDao.save(user);
			// 保存来源
			UserSource userSource = new UserSource();
			userSource.setUserId(user.getId());
			userSource.setSourceFrom(soucefrom);
			userSource.setOpenId(openId);
			userSourceService.save(userSource);
			log.info(SourceFromEnum.getEnumByCode(soucefrom).getName() + "用户OpenID:" + openId + "创建用户" + mobile + "!");

		}

		return user;
	}

	public User updateMobile(String mobile ,String smsCode ,User user) {
		if (!smsCodeService.isRightSmsCode(mobile, smsCode)) {
			throw new CustomBizException("手机验证码错误!");
		}
		User u = userDao.getUserByMobile(mobile);
		if(u!=null){
			throw new CustomBizException("手机号已绑定!");
		}
		user.setMobile(mobile);
		return update(user);
	}
	public User read(Long userId) {
		return userDao.findOne(userId);
	}

	public User update(User obj) {
		return this.userDao.save(obj);
	}

}
