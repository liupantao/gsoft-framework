package com.gsoft.framework.jpa.conf;
import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.WebStatFilter;
import com.gsoft.framework.core.log.ConfigRegisterLog;

/**
 * 配置druid监控统计功能
 * 配置Filter
 * @author liupantao
 *
 */
@Configuration
public class DruidStatFilter{
	@Bean
	public FilterRegistrationBean characterEncodingFilterRegistrationBean() {
		Filter druidWebStatFilter = new WebStatFilter();
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(druidWebStatFilter);
		registration.setName("druidWebStatFilter");
		registration.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		registration.addUrlPatterns("/*");
		ConfigRegisterLog.registeFilter(druidWebStatFilter, "连接池DruidStatFilter",this);
		return registration;
	}
}