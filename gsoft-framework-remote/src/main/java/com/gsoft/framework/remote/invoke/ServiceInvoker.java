/**
 * 
 */
package com.gsoft.framework.remote.invoke;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils.MethodFilter;

import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.orm.PagerRecords;
import com.gsoft.framework.core.web.view.Message;
import com.gsoft.framework.remote.ResCodeConstants;
import com.gsoft.framework.remote.annotation.InitializeProperty;
import com.gsoft.framework.remote.annotation.ResultMessage;
import com.gsoft.framework.remote.annotation.ServiceMapping;
import com.gsoft.framework.remote.data.PubContext;
import com.gsoft.framework.remote.data.ResContext;
import com.gsoft.framework.remote.method.InvocableSeriveMethod;
import com.gsoft.framework.remote.method.ServiceMethodSelector;
import com.gsoft.framework.taglib.convert.Convert;
import com.gsoft.framework.util.PropertyUtils;

/**
 * 
 * @author liupantao
 * 
 */
@Service("serviceInvoker")
@Transactional
public class ServiceInvoker implements ApplicationContextAware {

	private static final Log logger = LogFactory.getLog(ServiceInvoker.class);

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * 获取ESB bean实例
	 * 
	 * @param beanName
	 * @return
	 */
	public Object getEsbServiceBean(String beanName) {
		if (beanName == null) {
			throw new BusException(ResCodeConstants.ERROR_PARAM, "缺少服务参数[beanName].");
		}
		try {
			return applicationContext.getBean(beanName);
		} catch (BeansException e) {
			// logger.error("从上下文中获取实例" + beanName + "失败", e);
			throw new BusException(ResCodeConstants.SERVICE_NOTFOUNT, "未找到服务bean[" + beanName + "].");
		}
	}

	/**
	 * 获取ESB接口方法
	 * 
	 * @param beanName
	 * @param methodName
	 * @return
	 */
	public Method getEsbServiceMethod(Object bean, String methodName) {
		if (methodName == null) {
			throw new BusException(ResCodeConstants.ERROR_PARAM, "缺少服务参数[methodName].");
		}
		try {
			Method ServiceMethod = findServiceMethod(bean, methodName);
			if (ServiceMethod == null) {
				throw new BusException(ResCodeConstants.SERVICE_NOTFOUNT, "未找到方法ServiceMethod[" + getTargetClass(bean).getSimpleName() + "."
						+ methodName + "].");
			}
			return ServiceMethod;
		} catch (BusException e) {
			throw e;
		} catch (Exception e) {
			throw new BusException(ResCodeConstants.SERVICE_NOTFOUNT, "未找到方法ServiceMethod[" + getTargetClass(bean).getSimpleName() + "." + methodName
					+ "].");
		}
	}

	// 因为本方法为声明式服务，不能截取RuntimeException，所以要将异常处理放到RmiServiceFactory处理。
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResContext invoke(Object bean, Method serviceMethod, Map<String, List<Object>> params, PubContext pubContext) throws Exception {
		ResContext res = new ResContext();
		Object result = this.doInvokeMethod(params, bean, serviceMethod, pubContext);
		if (result == null) {
			return res;
		}
		Class<?> resultType = result.getClass();

		if (List.class.isAssignableFrom(resultType)) {
			// 集合类型的返回结果
			res.setData((List) result);
		} else if (PagerRecords.class.isAssignableFrom(resultType)) {
			// 分页对象的返回结果
			PagerRecords pagerRecords = (PagerRecords) result;
			res.setPagerRecords(pagerRecords);
//			res.setData(pagerRecords.getRecords());
//			res.setCount(pagerRecords.getTotalCount());
		} else if (Domain.class.isAssignableFrom(resultType)) {
			// 实体对象的返回结果
			res.setRecord((Domain) result);
		} else if (Message.class.isAssignableFrom(resultType)) {
			// 消息类型
			res.setMessage((Message) result);
		} else if (ResContext.class.isAssignableFrom(resultType)) {
			res = (ResContext) result;
		} else if (String.class.isAssignableFrom(resultType)) {
			res.setHtml(result.toString());
		} else if (Convert.class.isAssignableFrom(resultType)) {
			res.setRecord(((Convert) result).getRecord());
		}
		return res;
	}

	/**
	 * 查找服务方法
	 * 
	 * @param beanName
	 * @param methodName
	 * @return
	 */
	private Method findServiceMethod(Object bean, final String methodName) {

		final Class<?> beanClass = getTargetClass(bean);

		boolean isInterfaceMethod = false;
		for (Class<?> beanInterfaceClazz : beanClass.getInterfaces()) {
			if (ClassUtils.getMethodCountForName(beanInterfaceClazz, methodName) > 0) {
				isInterfaceMethod = true;
				break;
			}
		}
		// 如果不是接口方法，返回错误提示信息
		if (isInterfaceMethod) {
			Set<Method> methods = ServiceMethodSelector.selectMethods(beanClass, new MethodFilter() {
				public boolean matches(Method method) {
					return getMappingForMethod(method, beanClass, methodName) != null;
				}
			});
			if (methods.size() > 0) {
				return methods.toArray(new Method[methods.size()])[0];
			}
		} else {
			logger.error(methodName + "不是接口方法");
		}

		return null;
	}

	private Object doInvokeMethod(Map<String, List<Object>> params, Object bean, Method method, PubContext pubContext) throws Exception {
		Object result = null;

		//
		if (method != null) {
			InvocableSeriveMethod serviceMethod = new InvocableSeriveMethod(bean, method);
			// 因为本方法为声明式服务，不能截取RuntimeException，所以要将异常处理放到ServiceFactory处理。
			result = serviceMethod.invokeForRequest(params, pubContext);

			ResultMessage resultMessage = method.getAnnotation(ResultMessage.class);

			if (resultMessage != null) {
				Message message;
				if (result != null && Message.class.isAssignableFrom(result.getClass())) {
					return result;// 直接返回
				} else {
					String code = resultMessage.code();
					String info = resultMessage.info();
					if (info == null || "".equals(info)) {
						info = result.toString();
					}
					message = new Message(code, info);
					return message;
				}
			} else {
				ServiceMapping mapping = method.getAnnotation(ServiceMapping.class);
				// 强制接口
				try {
					if (result != null) {
						focreInitializeProperties(result, mapping.initializeProperties());
					}
				} catch (Exception e) {
					logger.warn("强制加载延迟数据失败.");
				}
			}
		}
		//
		return result;
	}

	/**
	 * 强制处理延迟加载对象
	 * 
	 * @param result
	 * @param initializeProperties
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void focreInitializeProperties(Object result, InitializeProperty[] initializeProperties) {
		if (initializeProperties != null && initializeProperties.length > 0) {
			//
			Class<?> resultType = result.getClass();
			List entityList = null;
			if (List.class.isAssignableFrom(resultType)) {
				// 集合类型
				entityList = (List) result;
			} else if (PagerRecords.class.isAssignableFrom(resultType)) {
				// 分页对象
				entityList = ((PagerRecords) result).getRecords();
			} else if (Domain.class.isAssignableFrom(resultType)) {
				// 普通实体
				entityList = new ArrayList<Domain>();
				entityList.add(result);
			}
			if (entityList != null) {
				for (Object entity : entityList) {
					focreDomainInitializeProperties(entity, initializeProperties);
				}
			}
		}
	}

	/**
	 * @param entity
	 * @param initializeProperties
	 */
	private void focreDomainInitializeProperties(Object entity, InitializeProperty[] initializeProperties) {
		for (InitializeProperty property : initializeProperties) {
			Object result = PropertyUtils.getSimplePropertyValue(entity, property.value());
			if (result != null) {
				Hibernate.initialize(result);
			}
		}
	}

	private Class<?> getTargetClass(Object bean) {
		return AopUtils.getTargetClass(bean);
	}

	/**
	 * @param method
	 * @param beanClass
	 * @param methodName
	 * @return
	 */
	private Method getMappingForMethod(Method method, Class<?> beanClass, String methodName) {
		if (method.getAnnotation(ServiceMapping.class) != null && methodName.equals(method.getName())) {
			return method;
		}
		return null;
	}

}
