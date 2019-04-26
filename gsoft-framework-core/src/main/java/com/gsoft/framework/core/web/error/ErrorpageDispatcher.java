package com.gsoft.framework.core.web.error;

/**
 * TODO (描述该文件做什么)
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface ErrorpageDispatcher {
	
	/** 
	 * TODO(描述这个方法的作用) 
	 * @param paramString
	 * @param paramThrowable
	 * @return 
	 */
	public String getErrorInfo(String paramString, Throwable paramThrowable);
	
}