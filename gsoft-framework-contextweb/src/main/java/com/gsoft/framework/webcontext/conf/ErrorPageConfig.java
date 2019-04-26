package com.gsoft.framework.webcontext.conf;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 异常页面处理
 * @author liupantao
 * @date 2018年1月5日
 *  
 */
@Configuration
public class ErrorPageConfig implements EmbeddedServletContainerCustomizer {

	@Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/errorpage/404.html"));
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errorpage/500.html"));
    }
	
}