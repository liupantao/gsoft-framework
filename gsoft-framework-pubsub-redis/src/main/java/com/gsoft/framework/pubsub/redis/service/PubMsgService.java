/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.pubsub.redis.service;

import com.gsoft.framework.core.dataobj.Domain;

/**
 * 消息广播服务
 * @author liupantao
 * @date 2018年7月13日
 *  
 */
public interface PubMsgService {
	
	/** 
	 * 广播消息
	 * @param channelName
	 * @param data 
	 */
	public void pubMsg(String channelName,Domain data);
	
}
