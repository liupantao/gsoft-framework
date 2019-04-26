/**
 * 
 */
package com.gsoft.framework.remote.data;

import javax.validation.ConstraintViolationException;

import org.springframework.transaction.TransactionSystemException;

import com.gsoft.framework.core.Constants;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.web.view.Message;

/**
 * 
 * @author liupantao
 * 
 */
@SuppressWarnings("rawtypes")
public class ExceptionResContext extends ResContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4348475841388396752L;

	public ExceptionResContext() {
	}
	
	public ExceptionResContext(Throwable exception) {
		Message errorMessage = null;
		if (exception instanceof BusException) {
			errorMessage = ((BusException) exception).getExceptionMessage();
		} else if (exception instanceof TransactionSystemException) {
			TransactionSystemException transactionSystemException = (TransactionSystemException) exception;
			if (transactionSystemException.getCause() != null
					&& transactionSystemException.getCause().getCause() != null
					&& transactionSystemException.getCause().getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException violationException = (ConstraintViolationException) transactionSystemException
						.getCause().getCause();
				errorMessage = new Message(Constants.ERROR_DOMAIN_VALIDATOR, violationException
						.getConstraintViolations().toString());
			} else {
				errorMessage = new Message(Constants.ERROR_DEFAULT_CODE, "执行事物失败:" + getCauseMessage(exception));
			}
		} else if (exception instanceof RuntimeException) {
			errorMessage = new Message(Constants.ERROR_DEFAULT_CODE, "服务调用异常："+getCauseMessage(exception));
		} else {
			errorMessage = new Message(Constants.ERROR_DEFAULT_CODE, "系统异常：" + getCauseMessage(exception));
		}

		this.setMessage(errorMessage);
	}

	private String getCauseMessage(Throwable exception) {
		Throwable cause = exception;
		while (cause.getCause() != null && !cause.getCause().getClass().equals(cause.getClass())) {
			cause = cause.getCause();
		}
		return cause.getMessage();
	}
}
