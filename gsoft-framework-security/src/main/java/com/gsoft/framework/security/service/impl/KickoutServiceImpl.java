/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.security.service.impl;

import java.io.Serializable;

import com.gsoft.framework.security.filter.AbstractKickoutSessionControlFilter;
import com.gsoft.framework.security.service.KickoutService;

/**
 * 踢人服务
 * 
 * @author LiuPeng
 * @date 2018年7月23日
 * 
 */
public class KickoutServiceImpl implements KickoutService {

	private AbstractKickoutSessionControlFilter sessionControlFilter;

	@Override
	public void kickout(Serializable kickoutSessionId, String msg) {
		if (sessionControlFilter != null) {
			sessionControlFilter.kickout(kickoutSessionId, msg);
		}
	}

	@Override
	public void kickout(String userId, String msg) {
		if (sessionControlFilter != null) {
			sessionControlFilter.kickout(userId, msg);
		}
	}

	public AbstractKickoutSessionControlFilter getSessionControlFilter() {
		return sessionControlFilter;
	}

	public void setSessionControlFilter(AbstractKickoutSessionControlFilter sessionControlFilter) {
		this.sessionControlFilter = sessionControlFilter;
	}

}
