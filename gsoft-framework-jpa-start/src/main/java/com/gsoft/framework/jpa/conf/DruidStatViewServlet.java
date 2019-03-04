package com.gsoft.framework.jpa.conf;

import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.gsoft.framework.core.log.ConfigRegisterLog;
import com.gsoft.framework.util.StringUtils;

/**
 * 配置druid监控统计功能 在SpringBoot项目中基于注解的配置，如果是web.xml配置，按规则配置即可
 * 
 * @author liupantao
 * 
 */
@Configuration
public class DruidStatViewServlet {

	@Value("${druid.allow:}")
	private String allow;
	@Value("${druid.deny:}")
	private String deny;
	@Value("${druid.loginUsername:}")
	private String loginUsername;
	@Value("${druid.loginPassword:}")
	private String loginPassword;
	@Value("${druid.resetEnable:false}")
	private String resetEnable;

	@Bean
	public ServletRegistrationBean druidStatViewServletRegistrationBean() {
		Servlet druidStatViewServlet = new StatViewServlet();
		ServletRegistrationBean registration = new ServletRegistrationBean(druidStatViewServlet);
		registration.setName("druidStatViewServlet");
		// IP白名单 (没有配置或者为空，则允许所有访问)
		if (StringUtils.isNotEmpty(allow)) {
			registration.addInitParameter("allow", allow);
		}
		if (StringUtils.isNotEmpty(deny)) {
			// IP黑名单 (存在共同时，deny优先于allow)
			registration.addInitParameter("deny", deny);
		}
		if(StringUtils.isNotEmpty(loginUsername)&&StringUtils.isNotEmpty(loginPassword)){
			registration.addInitParameter("loginUsername", loginUsername);
			registration.addInitParameter("loginPassword", loginPassword);
		}
		registration.addInitParameter("resetEnable", resetEnable);
		registration.addUrlMappings("/druid/*");
		ConfigRegisterLog.registeServlet(druidStatViewServlet, "连接池StatViewServlet",this);
		return registration;
	}
}