package com.gsoft.framework.taglib.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 装饰器提供类
 * @author liupantao
 *
 */
public class DecoratorProvider implements ApplicationContextAware {

	private List<String> decoratorList;

	public String getUserDecorator(String defaultDecorator) {
		return "";
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		initDecorators(applicationContext);
	}

	private void initDecorators(ApplicationContext applicationContext) {
		if (this.decoratorList == null) {
			Map<String, AbstractLayout> decoratorMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, AbstractLayout.class, true,
					false);
			decoratorList = new ArrayList<String>();
			for (Map.Entry<String, AbstractLayout> entry : decoratorMap.entrySet()) {
				String beanName = entry.getKey();
				if (beanName.startsWith(LayoutProvider.DECORATOR_PREFIX)) {
					decoratorList.add(beanName.substring(LayoutProvider.DECORATOR_PREFIX.length()));
				}
			}
		}
	}

}
