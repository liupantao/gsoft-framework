/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.pubsub.redis;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.pubsub.redis.log.PubsubLog;
import com.gsoft.framework.pubsub.redis.vo.PubMsgVo;

/**
 * 消息订阅接口
 * 
 * @author liupantao
 * @date 2018年7月13日
 * 
 */
public abstract class AbstractSubMsgReceiver<T extends Domain> {

	protected Log logger = LogFactory.getLog(getClass());

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Value("${spring.application.name}")
	private String applicationName;

	/**
	 * 获取订阅通道
	 * 
	 * @return
	 */
	public abstract String getChannel();

	/**
	 * 订阅消息
	 * 
	 * @param msg
	 */
	protected abstract void onMessage(T msg);

	/**
	 * 订阅消息
	 * 
	 * @param message
	 */
	public final void onMessage(Message message) {
		String msg = new String(message.getBody());
		try {
			PubMsgVo pubMsgVo = JSON.parseObject(msg, PubMsgVo.class);
			if (validateSub(pubMsgVo)) {
				PubsubLog.info("sub:" + msg);
				T data = JSON.parseObject(pubMsgVo.getData(), getTClass());
				onMessage(data);
			} else {
				PubsubLog.info("sub:消息已有其他节点订阅:" + pubMsgVo.getMsgId());
			}
		} catch (Exception e) {
			logger.error("消息订阅失败:" + msg, e);
		}
	}

	/**
	 * 是否需要广播
	 * 
	 * @return
	 */
	private boolean validateSub(PubMsgVo pubMsgVo) {
		if (signApplicationSub()) {
			String id = getChannel() + "_" + pubMsgVo.getMsgId();
			Boolean isSet = redisTemplate.boundValueOps(getCacheKey(id)).setIfAbsent("1");
			if (isSet) {
				redisTemplate.boundValueOps(getCacheKey(id)).expire(10, TimeUnit.SECONDS);
				return true;
			}
			return false;
		} else {
			return true;
		}
	}

	private final static String PUBSUB_KEY_PREFIX = "subsign:";

	/**
	 * 缓存key
	 * 
	 * @param id
	 * @return
	 */
	private String getCacheKey(String id) {
		return PUBSUB_KEY_PREFIX + applicationName + ":" + getClass().getName() + ":" + id;
	}

	@SuppressWarnings("unchecked")
	private Class<T> getTClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * 同一服务多节点 一个消息只有一个节点订阅
	 * 
	 * @return
	 */
	protected boolean signApplicationSub() {
		return true;
	}

}
