package com.gsoft.framework.security.conf;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.alibaba.fastjson.JSON;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.security.FormLoginRealm;
import com.gsoft.framework.security.UserService;
import com.gsoft.framework.security.authc.DefaultFormAuthenticationFilter;
import com.gsoft.framework.security.authc.UserHashedCredentialsMatcher;
import com.gsoft.framework.security.filter.KickoutSessionControlFilter;
import com.gsoft.framework.security.service.KickoutService;
import com.gsoft.framework.security.service.LoginLogService;
import com.gsoft.framework.security.service.impl.KickoutServiceImpl;
import com.gsoft.framework.security.session.CacheSessionDao;
import com.gsoft.framework.security.session.NavaWebSessionManager;
import com.gsoft.framework.util.StringUtils;

/**
 * Shiro 配置
 * 
 * @author liupantao
 * 
 */
@Configuration
public class ShiroConfig {

	private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

	@Value("${security.filterChainDefinitions}")
	private String filterChainDefinitions;

	@Value("${security.sessionTimeOut:1800}")
	private int sessionTimeOut;

	@Value("${security.vcodeCheck:false}")
	private boolean vcodeCheck;

	@Value("${security.maxSession:1}")
	private int maxSession;

	@Value("${security.kickoutAfter:false}")
	private boolean kickoutAfter;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private UserService userService;

	@Autowired
	private UserHashedCredentialsMatcher userHashedCredentialsMatcher;

	@Autowired(required = false)
	private LoginLogService loginLogService;

	@Bean(name = "realm")
	public FormLoginRealm shiroRealm() {
		FormLoginRealm realm = new FormLoginRealm();
		realm.setCacheManager(cacheManager);
		realm.setUserService(userService);
		realm.setCredentialsMatcher(userHashedCredentialsMatcher);
		return realm;
	}

	/**
	 * 注册DelegatingFilterProxy（Shiro）
	 *
	 * @param dispatcherServlet
	 * @return
	 * @author SHANHY
	 * @create 2016年1月13日
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		// 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");
		return filterRegistration;
	}

	@Bean
	public KickoutSessionControlFilter kickoutSessionFilter() {
		KickoutSessionControlFilter appKickoutSessionControlFilter = new KickoutSessionControlFilter();
		appKickoutSessionControlFilter.setCacheManager(cacheManager);
		appKickoutSessionControlFilter.setKickoutAfter(kickoutAfter);
		appKickoutSessionControlFilter.setMaxSession(maxSession);
		appKickoutSessionControlFilter.setSessionManager(getWebSessionManager());
		appKickoutSessionControlFilter.setUserService(userService);
		return appKickoutSessionControlFilter;
	}

	@Bean("kickoutService")
	public KickoutService getKickoutService() {
		KickoutServiceImpl kickoutService = new KickoutServiceImpl();
		kickoutService.setSessionControlFilter(kickoutSessionFilter());
		return kickoutService;
	}

	@Bean
	public SessionDAO getSessionDao() {
		CacheSessionDao cacheSessionDao = new CacheSessionDao(cacheManager, sessionTimeOut * 1000);
		return cacheSessionDao;
	}

	@Bean
	public NavaWebSessionManager getWebSessionManager() {
		NavaWebSessionManager manager = new NavaWebSessionManager();
		SessionDAO sessionDao = getSessionDao();
		manager.setCacheManager(cacheManager);// 加入缓存管理器
		manager.setSessionDAO(sessionDao);// 设置SessionDao
		manager.setDeleteInvalidSessions(true);// 删除过期的session
		manager.setGlobalSessionTimeout(sessionTimeOut * 1000);// 设置全局session超时时间
		manager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session

		return manager;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager() {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		dwsm.setRealm(shiroRealm());
		dwsm.setCacheManager(cacheManager);
		dwsm.setSessionManager(getWebSessionManager());
		return dwsm;
	}

	/**
	 * ShiroFilter<br/>
	 * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
	 * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
	 * 
	 * @param myShiroRealm
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());

		shiroFilterFactoryBean.setFilters(getFilters());

		shiroFilterFactoryBean.setLoginUrl("/login.html");
		// 登录成功后要跳转的连接
		shiroFilterFactoryBean.setSuccessUrl("/index.html");

		shiroFilterFactoryBean.setUnauthorizedUrl("/error.html");

		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		
		filterChainDefinitionMap.put("/login.html", "authc");
		filterChainDefinitionMap.put("/logout.html", "anon");
		filterChainDefinitionMap.put("/Kaptcha.jpg", "anon");
		filterChainDefinitionMap.put("/error.html", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/browser/**", "anon");

		if (StringUtils.isNotEmpty(filterChainDefinitions)) {
			String[] filterChainDefinitionArry = filterChainDefinitions.split(",");
			for (String filterChainDefinition : filterChainDefinitionArry) {
				String[] filterChains = filterChainDefinition.split("=");
				if (filterChains.length == 2) {
					filterChainDefinitionMap.put(filterChains[0].trim(), filterChains[1].trim());
				} else {
					throw new BusException("filterChainDefinitionArry 解析失败：" + filterChainDefinition);
				}
			}
			logger.info("加载权限配置:" + JSON.toJSONString(filterChainDefinitionArry) + ",完成...");
		} else {
			logger.warn("权限配置为空..." + filterChainDefinitions);
		}

		filterChainDefinitionMap.put("/**", "kickout,authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	protected Map<String, Filter> getFilters() {
		Map<String, Filter> filters = new HashMap<String, Filter>();
		DefaultFormAuthenticationFilter formAuthenticationFilter = new DefaultFormAuthenticationFilter();
		formAuthenticationFilter.setVcodeCheck(vcodeCheck);
		formAuthenticationFilter.setSessionTimeOut(sessionTimeOut);
		formAuthenticationFilter.setLoginLogService(loginLogService);
		formAuthenticationFilter.setUserService(userService);
		filters.put("authc", formAuthenticationFilter);
		
		KickoutSessionControlFilter kickoutFilter = kickoutSessionFilter();
		kickoutFilter.setUserService(userService);
		filters.put("kickout", kickoutFilter);
		return filters;
	}

}