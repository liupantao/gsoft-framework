package com.gsoft.framework.core.web.error;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;

/**
 * TODO (描述该文件做什么)
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
@Component
public class ErrorpageDispatcherImpl implements ErrorpageDispatcher, ApplicationContextAware {
	
	private List<IErrorpageAdapter> errorpageAdapters;

	@Override
	public String getErrorInfo(String errorCode, Throwable exception) {
		if (this.errorpageAdapters != null) {
			for (IErrorpageAdapter errorpageAdapter : this.errorpageAdapters) {
				if (errorpageAdapter.supports(errorCode)) {
					return errorpageAdapter.buildErrorInfo(exception);
				}
			}
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		initErrorpageAdapters(context);
	}

	private void initErrorpageAdapters(ApplicationContext context) {
		if (this.errorpageAdapters == null) {
			Map<String, IErrorpageAdapter> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context,
					IErrorpageAdapter.class, true, false);
			if (beans != null) {
				this.errorpageAdapters = new ArrayList<IErrorpageAdapter>(beans.values());
				Collections.sort(this.errorpageAdapters, new OrderComparator());
			}
		}
	}
}