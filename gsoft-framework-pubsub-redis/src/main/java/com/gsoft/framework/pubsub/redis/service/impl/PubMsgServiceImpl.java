/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.pubsub.redis.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.pubsub.redis.log.PubsubLog;
import com.gsoft.framework.pubsub.redis.service.PubMsgService;
import com.gsoft.framework.pubsub.redis.vo.PubMsgVo;

/**
 * 消息广播服务
 * 
 * @author liupantao
 * @date 2018年7月13日
 * 
 */
@Service("pubMsgService")
public class PubMsgServiceImpl implements PubMsgService {

	private Log logger = LogFactory.getLog(getClass());

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void pubMsg(String channelName, Domain data) {
		try {
			PubMsgVo pubMsgVo = new PubMsgVo(data);
			String json = JSON.toJSONString(pubMsgVo);
			stringRedisTemplate.convertAndSend(channelName, json);
			PubsubLog.info("pub:" + channelName + "-" + data.toString());
		} catch (Exception e) {
			logger.error("广播消息失败:" + channelName + "-" + data.toString(), e);
		}
	}

}
