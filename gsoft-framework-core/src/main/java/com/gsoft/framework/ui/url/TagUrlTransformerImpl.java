package com.gsoft.framework.ui.url;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;

/**
 * TODO (描述该文件做什么)
 * @author liupantao
 * @date 2017年10月17日
 *  
 */
public class TagUrlTransformerImpl implements TagUrlTransformer, ApplicationContextAware {
	private List<UrlTransformerAdapter> urlTransformerAdapters;

	@Override
	public String transform(String url) {
		if (this.urlTransformerAdapters != null) {
			for (UrlTransformerAdapter adapter : this.urlTransformerAdapters) {
				if (adapter.supports(url)) {
					return adapter.transform(url);
				}
			}
		}
		return url;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		initUrlTransformerAdapters(applicationContext);
	}

	private void initUrlTransformerAdapters(ApplicationContext applicationContext) {
		if (this.urlTransformerAdapters == null) {
			 Map<String, UrlTransformerAdapter> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, UrlTransformerAdapter.class,
					true, false);

			if (matchingBeans != null) {
				this.urlTransformerAdapters = new ArrayList<UrlTransformerAdapter>(matchingBeans.values());
				Collections.sort(this.urlTransformerAdapters, new OrderComparator());
			}
		}
	}
}