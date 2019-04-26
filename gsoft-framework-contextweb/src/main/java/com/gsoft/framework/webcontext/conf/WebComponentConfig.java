package com.gsoft.framework.webcontext.conf;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.googlecode.webutilities.filters.CompressionFilter;
import com.gsoft.framework.core.filter.AccessFilter;
import com.gsoft.framework.core.log.ConfigRegisterLog;
import com.gsoft.framework.security.DefaultKaptchaServlet;

/**
 * web组件配置(原web.xml)
 * 
 * @author liupantao
 * 
 */
@Component
public class WebComponentConfig {

	@Bean
	public FilterRegistrationBean accessFilterRegistration() {
		Filter accessFilter = new AccessFilter();
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(accessFilter);
		registration.setName("accessFilter");
		registration.addUrlPatterns("/*");
		ConfigRegisterLog.registeFilter(accessFilter, "AccessFilter", this);
		return registration;
	}

	@Bean
	public FilterRegistrationBean compressionFilterRegistration() {
		Filter compressionFilter = new CompressionFilter();
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(compressionFilter);
		registration.setName("compressionFilter");
		registration.addInitParameter("ignoreURLPattern", ".+\\.rpt");
		registration.addUrlPatterns("*.jsp");
		registration.addUrlPatterns("*.js");
		registration.addUrlPatterns("*.css");
		registration.addUrlPatterns("*.gif");
		registration.addUrlPatterns("*.png");
		registration.addUrlPatterns("*.jpg");
		ConfigRegisterLog.registeFilter(compressionFilter, "压缩Filter", this);
		return registration;
	}

	@Bean
	public FilterRegistrationBean characterEncodingFilterRegistration() {
		Filter characterEncodingFilter = new CharacterEncodingFilter();
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(characterEncodingFilter);
		registration.setName("characterEncodingFilter");
		registration.addInitParameter("encoding", "UTF-8");
		registration.addInitParameter("forceEncoding", "true");
		registration.addUrlPatterns("/*");
		ConfigRegisterLog.registeFilter(characterEncodingFilter, "字符集Filter", this);
		return registration;
	}

	@Bean
	public ServletRegistrationBean defaultKaptchaServletRegistration() {
		Servlet defaultKaptchaServlet = new DefaultKaptchaServlet();
		ServletRegistrationBean registration = new ServletRegistrationBean(defaultKaptchaServlet);
		registration.setName("defaultKaptchaServlet");
		registration.addInitParameter("kaptcha.border", "no");
		registration.addInitParameter("kaptcha.textproducer.font.color", "black");
		registration.addInitParameter("kaptcha.background.clear.from", "white");
		registration.addInitParameter("kaptcha.image.width", "160");
		registration.addInitParameter("kaptcha.image.height", "40");
		registration.addInitParameter("kaptcha.textproducer.font.size", "36");
		registration.addInitParameter("kaptcha.textproducer.font.names", "comic Sans MS");
		registration.addInitParameter("kaptcha.textproducer.char.string", "1234567890");
		registration.addInitParameter("kaptcha.textproducer.char.length", "4");
		registration.addInitParameter("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
		registration.addUrlMappings("/Kaptcha.jpg");
		ConfigRegisterLog.registeServlet(defaultKaptchaServlet, "验证码Servlet", this);
		return registration;
	}

}
