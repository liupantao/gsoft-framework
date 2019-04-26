/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.pubsub.redis.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * pubsub日志
 * 
 * @author liupantao
 * @date 2018年7月4日
 * 
 */
public class PubsubLog {

	private final static String LOG_NAME = "pubsubLog";
	private static Log logger = LogFactory.getLog(LOG_NAME);

	/**
	 * info
	 * 
	 * @param msg
	 */
	public static void info(String info) {
		logger.info(info);
	}
}
