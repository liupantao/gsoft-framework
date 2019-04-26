package com.gsoft.framework.security.session;

import java.io.Serializable;
import java.util.Date;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gsoft.framework.security.AccountPrincipal;
import com.gsoft.framework.security.dao.SimpleSessionDao;
import com.gsoft.framework.security.entity.SimpleSessionEntity;
import com.gsoft.framework.security.util.SerializableUtils;
import com.gsoft.framework.util.DateUtils;
import com.gsoft.framework.util.SecurityUtils;

/**
 * SessionEntityDao
 * 
 * @author liupantao
 *
 */
@Transactional
public class SessionEntityDao extends CacheSessionDao {

	/**
	 * 创建一个新的实例 SessionEntityDao.
	 *
	 * @param cacheManager
	 * @param expireTime
	 */
	public SessionEntityDao(CacheManager cacheManager, long expireTime) {
		super(cacheManager, expireTime);
	}

	@Autowired
	private SimpleSessionDao sessionDao;

	@Override
	public Serializable create(Session session) {
		// 先保存到缓存中
		Serializable sessionId = super.create(session);
		if (sessionDao == null) {
			return sessionId;
		}
		// 保存session到数据库
		SimpleSessionEntity sessionEntity = new SimpleSessionEntity();
		sessionEntity.setId(sessionId.toString());
		sessionEntity.setCreateTime(DateUtils.getToday("yyyyMMddHHmmss"));
		sessionEntity.setSession(SerializableUtils.serializ(session));
		sessionDao.save(sessionEntity);
		return sessionId;
	}

	private final static String DBCACHE_KEY = "db_cache";

	@Override
	public void update(Session session) throws UnknownSessionException {
		if (SecurityUtils.getAccount() != null && session.getAttribute(DBCACHE_KEY) == null) {
			// 如果登录成功，更新用户id
			AccountPrincipal user = SecurityUtils.getAccount();
			if (sessionDao != null && user != null) {
				SimpleSessionEntity sessionEntity = sessionDao.findOneByProperty("id", session.getId().toString());
				if (sessionEntity == null) {
					sessionEntity = new SimpleSessionEntity();
					sessionEntity.setId(session.getId().toString());
					sessionEntity.setCreateTime(DateUtils.getToday("yyyyMMddHHmmss"));
				}
				sessionEntity.setUsername(user.getLoginName());
				sessionEntity.setSession(SerializableUtils.serializ(session));
				sessionEntity.setUpdateTime(DateUtils.getToday("yyyyMMddHHmmss"));
				sessionDao.save(sessionEntity);

				session.setAttribute(DBCACHE_KEY, "1");
			}
		}
		super.update(session);
	}

	@Override
	public Session readSession(Serializable sessionId) throws UnknownSessionException {
		Session session = null;
		try {
			session = super.readSession(sessionId);
		} catch (Exception e) {
		}
		// 如果session已经被删除，则从数据库中查询session
		if (sessionDao != null && session == null) {
			SimpleSessionEntity sessionEntity = sessionDao.findOneByProperty("id", sessionId);
			if (sessionEntity != null) {
				String sessionStr64 = sessionEntity.getSession();
				session = SerializableUtils.deserializ(sessionStr64);
				((SimpleSession) session).setLastAccessTime(new Date());
				super.update(session);
			}
		}
		return session;
	}

	@Override
	public void delete(Session session) {
		super.delete(session);
		if (sessionDao == null) {
			return;
		}
		sessionDao.delete(session.getId().toString());
	}

}