/**
 * 
 */
package com.gsoft.framework.core.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;

import com.gsoft.framework.core.web.view.Message;


/**
 * 异常message接口
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface ExceptionMessage {
	/**
	 * 获取异常输出信息
	 * @return
	 */
	public Message getExceptionMessage();
	
	/** 
	 * 获取异常输出信息
	 * @param messageSource
	 * @param locale
	 * @return 
	 */
	public Message getExceptionMessage(MessageSource messageSource,Locale locale);
}
