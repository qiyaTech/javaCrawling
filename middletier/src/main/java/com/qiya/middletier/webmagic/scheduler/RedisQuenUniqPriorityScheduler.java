package com.qiya.middletier.webmagic.scheduler;

import java.util.Set;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * the redis scheduler with priority
 * 
 * @author sai Created by sai on 16-5-27.
 */
public class RedisQuenUniqPriorityScheduler extends RedisScheduler {

	private static Logger log = LoggerFactory.getLogger(RedisQuenUniqPriorityScheduler.class);


	private static final String QUEUESET_PREFIX = "queueset_";

	private static final String ZSET_PREFIX = "zset_";

	private static final String QUEUE_PREFIX = "queue_";

	private static final String NO_PRIORITY_SUFFIX = "_zore";

	private static final String PLUS_PRIORITY_SUFFIX = "_plus";

	private static final String MINUS_PRIORITY_SUFFIX = "_minus";

	public RedisQuenUniqPriorityScheduler(String host) {
		super(host);
	}

	public RedisQuenUniqPriorityScheduler(JedisPool pool) {
		super(pool);
	}


	@Override
	protected void pushWhenNoDuplicate(Request request, Task task) {
		try(Jedis jedis = pool.getResource();) {
				if (request.getPriority() > 0)
					jedis.zadd(getZsetPlusPriorityKey(task), request.getPriority(), request.getUrl());
				else if (request.getPriority() < 0)
					jedis.zadd(getZsetMinusPriorityKey(task), request.getPriority(), request.getUrl());
				else
					jedis.lpush(getQueueNoPriorityKey(task), request.getUrl());

				setExtrasInItem(jedis, request, task);
		}
	}

	@Override
	public boolean isDuplicate(Request request, Task task) {
		boolean var5;
		try(Jedis jedis = pool.getResource();) {
				boolean isDuplicate = jedis.sismember(this.getQueueSetKey(task), request.getUrl()).booleanValue();
				if (!isDuplicate) {
					jedis.sadd(this.getQueueSetKey(task), new String[]{request.getUrl()});
				}
				var5 = isDuplicate;
		}
		return var5;
	}

	@Override
	public synchronized Request poll(Task task) {

		try(Jedis jedis = pool.getResource();) {
				String url = getRequest(jedis, task);
				if (StringUtils.isBlank(url))
					return null;
				return getExtrasInItem(jedis, url, task);
		}

	}

	private String getRequest(Jedis jedis, Task task) {
		String url;
		Set<String> urls = jedis.zrevrangeByScore(getZsetPlusPriorityKey(task), "+inf", "-inf", 0, 1);
		if (urls.isEmpty()) {

			url = jedis.lpop(getQueueNoPriorityKey(task));
			if (StringUtils.isBlank(url)) {
				urls = jedis.zrevrangeByScore(getZsetMinusPriorityKey(task), "+inf", "-inf", 0, 1);
				// jedis.zrevrange(getZsetMinusPriorityKey(task), 0, 0);
				if (!urls.isEmpty()) {
					url = urls.toArray(new String[0])[0];
					jedis.zrem(getZsetMinusPriorityKey(task), url);
				}
			}
		} else {
			url = urls.toArray(new String[0])[0];
			jedis.zrem(getZsetPlusPriorityKey(task), url);
		}

		if (url != null && url.length() > 0 && jedis.exists(getQueueSetKey(task))) {
			jedis.srem(getQueueSetKey(task), url);
		}
		return url;
	}

	private String getZsetPlusPriorityKey(Task task) {
		return ZSET_PREFIX + task.getUUID() + PLUS_PRIORITY_SUFFIX;
	}

	private String getQueueNoPriorityKey(Task task) {
		return QUEUE_PREFIX + task.getUUID() + NO_PRIORITY_SUFFIX;
	}

	private String getZsetMinusPriorityKey(Task task) {
		return ZSET_PREFIX + task.getUUID() + MINUS_PRIORITY_SUFFIX;
	}

	private void setExtrasInItem(Jedis jedis, Request request, Task task) {
		if (request.getExtras() != null) {
			String field = DigestUtils.shaHex(request.getUrl());
			String value = JSON.toJSONString(request);
			jedis.hset(getItemKey(task), field, value);
		}
	}

	private Request getExtrasInItem(Jedis jedis, String url, Task task) {
		String key = getItemKey(task);
		String field = DigestUtils.shaHex(url);
		byte[] bytes = jedis.hget(key.getBytes(), field.getBytes());
		if (bytes != null)
			return JSON.parseObject(new String(bytes), Request.class);
		return new Request(url);
	}

	public RedisQuenUniqPriorityScheduler resetDuplicateCheck(String uuid) {
		try (	Jedis jedis = this.pool.getResource()){
				jedis.del(QUEUESET_PREFIX + uuid);
				jedis.del(getZsetMinusPriorityKey(new Task() {
					@Override
					public String getUUID() {
						return uuid;
					}

					@Override
					public Site getSite() {
						return null;
					}
				}));
				jedis.del(getQueueNoPriorityKey(new Task() {
					@Override
					public String getUUID() {
						return uuid;
					}

					@Override
					public Site getSite() {
						return null;
					}
				}));
				jedis.del(getZsetPlusPriorityKey(new Task() {
					@Override
					public String getUUID() {
						return uuid;
					}

					@Override
					public Site getSite() {
						return null;
					}
				}));
		}


		return this;
	}

	@Override
	public int getLeftRequestsCount(Task task) {
		int var4;
		try(Jedis jedis = this.pool.getResource();) {
			Long size = jedis.llen(this.getQueueKey(task));
			var4 = size.intValue();

		}
		return var4;
	}
    @Override
	public int getTotalRequestsCount(Task task) {
		int var4;
		try(Jedis jedis = this.pool.getResource();) {
				Long size = jedis.scard(this.getSetKey(task));
				var4 = size.intValue();
		}
		return var4;
	}
	protected String getQueueSetKey(Task task) {
		return QUEUESET_PREFIX + task.getUUID();
	}
}
