package com.qiya.middletier.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiya.middletier.dao.UserSourceDao;
import com.qiya.middletier.model.UserSource;

/**
 * Created by qiyamac on 16/6/15.
 */
@Service
public class UserSourceService {

	private static Logger log = LoggerFactory.getLogger(UserSourceService.class);

	@Autowired
	private UserSourceDao userSourceDao;

	public UserSource getUserSource(String openid, String sourcefrom) {
		return userSourceDao.getUserSourceByOpenId(openid, sourcefrom);
	}

	public List<UserSource> getUserSource(Long userId) {
		return userSourceDao.findUserSourceByUserId(userId);
	}

	public UserSource save(UserSource userSource) {
		return userSourceDao.save(userSource);
	}
}
