package com.gsoft.framework.security.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.security.AbstractFormUserInfo;
import com.gsoft.framework.security.IUser;
import com.gsoft.framework.security.UserService;

/**
 * 并发登录人数控制
 * 
 * @author LiuPeng
 * @date 2018年5月3日
 * 
 */
public abstract class AbstractKickoutSessionControlFilter extends AccessControlFilter {
	private UserService userService;

	protected Log logger = LogFactory.getLog(getClass());

	private boolean kickoutAfter = false; // 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	private int maxSession = -1; // 同一个帐号最大会话数 默认1
	private SessionManager sessionManager;
	private Cache<String, Deque<Serializable>> cache;

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("shiro-KickoutSessionCache");
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 是否允许访问，返回true表示允许
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	/**
	 * 表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）。
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		Session session = subject.getSession();
		
		//判断如果token登录的，判断token是否正确，否则踢开
		Object principal = subject.getPrincipal();
		if (principal != null && principal instanceof IUser) {
			IUser user = (IUser) principal;
			AbstractFormUserInfo userInfo = (AbstractFormUserInfo)userService.getRealmUserInfo(user);
			if("token".equals(userInfo.getLoginType())) {
				if(!userInfo.getTokenCode().equals(WebUtils.getCleanParam(request, "token"))) {
					Object msg = session.getAttribute("kickout_msg");
					// 会话被踢出了
					try {
						subject.logout();
					} catch (Exception e) {
					}
					request.setAttribute("error", msg == null ? "token无效，您已被剔除" : msg.toString());
					afterKickout(request, response);
					return false;
				}
			}
		}
		
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			// 如果没有登录，直接进行之后的流程
			return true;
		}

		String username = getKickoutUsername(subject);
		Serializable sessionId = session.getId();

		// 初始化用户的队列放到缓存里
		Deque<Serializable> deque = cache.get(username);
		if (deque == null) {
			deque = new LinkedList<Serializable>();
		}

		// 如果队列里没有此sessionId，且用户没有被踢出；放入队列
		if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
			deque.push(sessionId);
		}

		if (maxSession > 0) {
			// 如果队列里的sessionId数超出最大会话数，开始踢人
			while (deque.size() > maxSession) {
				Serializable kickoutSessionId = null;
				if (kickoutAfter) { // 如果踢出后者
					kickoutSessionId = deque.getFirst();
					kickoutSessionId = deque.removeFirst();
				} else { // 否则踢出前者
					kickoutSessionId = deque.removeLast();
				}
				try {
					Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
					if (kickoutSession != null) {
						// 设置会话的kickout属性表示踢出了
						kickoutSession.setAttribute("kickout", true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		cache.put(username, deque);

		// 如果被踢出了，直接退出，重定向到踢出后的地址
		if (session.getAttribute("kickout") != null) {
			Object msg = session.getAttribute("kickout_msg");
			// 会话被踢出了
			try {
				subject.logout();
			} catch (Exception e) {
			}
			request.setAttribute("error", msg == null ? "其它设备登录，您已被剔除" : msg.toString());
			afterKickout(request, response);
			// WebUtils.issueRedirect(request, response, kickoutUrl);
			return false;
		}
		return true;
	}

	/**
	 * 根据sessionId踢人
	 * 
	 * @param kickoutSessionId
	 * @param msg
	 * @throws BusException
	 */
	public void kickout(Serializable kickoutSessionId, String msg) {
		try {
			Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
			if (kickoutSession != null) {
				// 设置会话的kickout属性表示踢出了
				kickoutSession.setAttribute("kickout", true);
				kickoutSession.setAttribute("kickout_msg", msg);
			}
		} catch (Exception e) {
			logger.error("根据sessionId剔除用户失败:" + kickoutSessionId);
		}
	}

	/**
	 * 根据用户名踢人
	 * 
	 * @param userId
	 * @param msg
	 */
	public void kickout(String userId, String msg) {
		Deque<Serializable> deque = cache.get(userId);
		if (deque != null) {
			while (deque.size() > 0) {
				Serializable kickoutSessionId = deque.removeFirst();
				kickout(kickoutSessionId, msg);
			}
		}
	}

	/**
	 * 获取登录用户名（踢人认证）
	 * 
	 * @param subject
	 * @return
	 */
	protected abstract String getKickoutUsername(Subject subject);

	/**
	 * 被剔除后操作
	 * 
	 * @param request
	 * @param response
	 */
	protected abstract void afterKickout(ServletRequest request, ServletResponse response);

}