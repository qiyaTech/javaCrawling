package com.qiya.middletier.service;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.qiya.framework.baselib.util.sms.SmsCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qiya.framework.exception.CustomBizException;
import com.qiya.framework.coreservice.sms.SmsService;
import com.qiya.framework.middletier.dao.CaptchaDao;
import com.qiya.framework.middletier.model.Captcha;
import com.qiya.framework.model.ApiCodeEnum;

/**
 * Created by admin on 16/5/13.
 */
@Service
public class SmsCodeService {
	@Autowired
	private SmsService smsService;

	@Autowired
	private CaptchaDao captchaDao;

	@Autowired
	private SmsCodeUtil smsUtil;

	// 失效时间3分钟
	@Value("${ snscode.exptime:3}")
	int exptime = 3;
	// 短信间隔时间
	@Value("${snscode.intervaltime:1}")
	int intervaltime = 1;

	/**
	 * 校验短信验证码是否正确
	 * 
	 * @param sender
	 * @param code
	 */
	public void checkSmsCode(String sender, String code) {
		if (!isRightSmsCode(sender, code)) {
			throw new CustomBizException("短信验证码不正确!");
		}
	}

	/**
	 * 检查验证是否有效期
	 * 
	 * @param sender 发送手机手机号
	 * @param code 验证码
	 * @return
	 */
	public boolean isRightSmsCode(String sender, String code) {
		List<Captcha> list = captchaDao.getCaptchBySender(sender);
		if (list.size() == 0) {
			return false;
		}
		Captcha captcha = list.get(0);
		if (captcha == null || !captcha.getCaptcha().equals(code)) {
			return false;
		}
		Long ExpireTime = captcha.getExpireTime().getTime();
		Long systime = System.currentTimeMillis();
		if (systime > ExpireTime) {
			return false;
		}
		return true;
	}

	/**
	 * 检验发送是否频繁
	 * 
	 * @param sender
	 * @return
	 */
	public void checkSendCaptchaTime(String sender) {
		if (isSendCaptchaTime(sender)) {
			throw new CustomBizException(ApiCodeEnum.FAIL, "验证码发送频繁!");
		}
	}

	/**
	 * 验证发送时间
	 * 
	 * @param sender
	 */
	public boolean isSendCaptchaTime(String sender) {
		List<Captcha> list = captchaDao.getCaptchBySender(sender);
		if (list.size() == 0) {
			return false;
		}
		Captcha captcha = list.get(0);
		// 过期时间减去三分钟等于
		Long createtime = (captcha.getExpireTime().getTime()) / 1000 - exptime * 60;
		Long systime = System.currentTimeMillis() / 1000;
		Long diff = systime - createtime;
		if (diff <= 60 * intervaltime) {
			return true;
		}
		return false;
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile 手机号码
	 */
	public void sendCaptchaCode(String mobile) {

		//生成验证码
		String code = getCode(); // 验证码
		//发送验证码
		smsService.verify(code,exptime,mobile);
		//保存验证码信息
		Captcha captcha = new Captcha();
		captcha.setCaptcha(code);
		captcha.setSender(mobile);
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, exptime);
		captcha.setExpireTime(nowTime.getTime());
		captchaDao.save(captcha);
	}

	/**
	 * 发送短信验证码
	 *
	 * @param mobile 手机号码
	 */
	public void sendSmsCode(String mobile) {

		//生成验证码
		String code = getCode(); // 验证码
		//发送验证码
		String content="您的验证码为"+code+",有效期为"+exptime+"分钟。";
		smsUtil.sendMessage(mobile,content);
		//保存验证码信息
		Captcha captcha = new Captcha();
		captcha.setCaptcha(code);
		captcha.setSender(mobile);
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, exptime);
		captcha.setExpireTime(nowTime.getTime());
		captchaDao.save(captcha);
	}

	public void sendSmsCode(String mobile,String content) {
		smsUtil.sendMessage(mobile,content);
	}


	// 随机生产验证码
	public String getCode() {
		String salt = "";
		String[] code = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		Random rd = new Random();
		for (int i = 0; i < 6; i++) {
			salt = salt + code[rd.nextInt(code.length - 1)];
		}
		return salt;
	}
}
