package com.gsoft.framework.core.web.error;

/**
 * TODO (描述该文件做什么)
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public abstract interface IErrorpageAdapter {
	/** 
	 * TODO(描述这个方法的作用) 
	 * @param paramString
	 * @return 
	 */
	public abstract boolean supports(String paramString);

	/** 
	 * TODO(描述这个方法的作用) 
	 * @param paramThrowable
	 * @return 
	 */
	public abstract String buildErrorInfo(Throwable paramThrowable);
}