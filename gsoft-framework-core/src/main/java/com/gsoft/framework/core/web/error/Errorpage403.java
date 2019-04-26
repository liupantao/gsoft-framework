package com.gsoft.framework.core.web.error;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * TODO (描述该文件做什么)
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
@Component
public class Errorpage403 implements IErrorpageAdapter, Ordered {
	
	@Override
	public boolean supports(String errorCode) {
		return "403".equals(errorCode);
	}

	@Override
	public String buildErrorInfo(Throwable exception) {
		return "访问权限不足";
	}

	@Override
	public int getOrder() {
		return 99;
	}
}