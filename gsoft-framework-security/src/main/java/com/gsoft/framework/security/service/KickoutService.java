/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.security.service;

import java.io.Serializable;

/**
 * 踢人
 * @author liupantao
 * @date 2018年7月23日
 *  
 */
public interface KickoutService {

	/** 
	 * 根据sessionId踢人
	 * @param kickoutSessionId
	 * @param msg 
	 */
	public void kickout(Serializable kickoutSessionId, String msg);
	
	
	/** 
	 * 根据用户名踢人
	 * @param userId
	 * @param msg 
	 */
	public void kickout(String userId, String msg);
}
