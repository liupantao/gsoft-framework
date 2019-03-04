/**
 * 
 */
package com.gsoft.framework.core.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;

import com.gsoft.framework.core.Constants;
import com.gsoft.framework.core.context.Config;
import com.gsoft.framework.core.web.view.Message;

/**
 * ExceptionUtils
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class ExceptionUtils {
	
	private static final Log logger = LogFactory.getLog(ExceptionUtils.class);
	
	/**
	 * @param ex
	 * @return
	 */
	public static Message getErrorMessage(Exception ex,MessageSource messageSource,Locale locale) {
		
		logTrace(ex);
		String msg;
		String msgCode;
		if(ex instanceof ExceptionMessage){
			return ((ExceptionMessage)ex).getExceptionMessage(messageSource,locale);
		}
//		else if(ex instanceof org.hibernate.validator.InvalidStateException){
//			return new InvalidMessage(((org.hibernate.validator.InvalidStateException) ex).getInvalidValues());
//		}
		else{
			Throwable cause = ex.getCause();
			while(cause!=null){
				if(cause instanceof ExceptionMessage ){
					return ((ExceptionMessage)cause).getExceptionMessage(messageSource,locale);
				}
				cause = cause.getCause();
			}
			//其他系统异常
			msg = "系统异常:"+ex.getMessage();
			msgCode = Constants.ERROR_DEFAULT_CODE;
			ex.printStackTrace();
		}
		//TODO 其他类型的错误分类
		
		return new Message(msgCode,msg);
	}
	
	public static void logTrace(Throwable cause){
		String  traceError = Config.getInstance().getProperty(Constants.PROP_TRACE_ERROR);
		if(Boolean.valueOf(traceError)&&cause!=null){
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			PrintStream sw = new PrintStream(byteStream);
			cause.printStackTrace(sw);
			String traces = byteStream.toString();
			logger.error(traces);
		}
	}

}
