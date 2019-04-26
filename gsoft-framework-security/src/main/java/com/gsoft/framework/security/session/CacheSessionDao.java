package com.gsoft.framework.security.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

public class CacheSessionDao extends AbstractSessionDAO {

	private static final String REDIS_SHIRO_SESSION_CACHE = "session";

	// Session超时时间，单位为毫秒
	private long expireTime = 1800000;

	private Cache<Serializable, Session> sessionCache;


	public CacheSessionDao(CacheManager cacheManager,long expireTime) {
		super();
		this.expireTime = expireTime;
		sessionCache = cacheManager.getCache(REDIS_SHIRO_SESSION_CACHE);
	}

	// 更新session
	@Override
	public void update(Session session) throws UnknownSessionException {
		if (session == null || session.getId() == null) {
			return;
		}
		session.setTimeout(expireTime);
		sessionCache.put(session.getId(), session);
	}

	// 删除session
	@Override
	public void delete(Session session) {
		if (null == session) {
			return;
		}
		sessionCache.remove(session.getId());
	}

	/*
	 * 获取活跃的session，可以用来统计在线人数.
	 */
	@Override
	public Collection<Session> getActiveSessions() {
		return sessionCache.values();
	}

	/*
	 * 加入session
	 */
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		sessionCache.put(session.getId(), session);
		return sessionId;
	}

	/*
	 * 读取session
	 */
	@Override
	protected Session doReadSession(Serializable sessionId) {
		if (sessionId == null) {
			return null;
		}
		return sessionCache.get(sessionId);
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

}