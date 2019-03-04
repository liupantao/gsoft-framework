package com.gsoft.framework.core.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * bean注册日志
 * 
 * @author liupantao
 * 
 */
public class ConfigRegisterLog {

	private static final Log logger = LogFactory.getLog(ConfigRegisterLog.class);

	public static void registeBean(Object bean, String caption, Object from) {
		logger.info("注册Bean：" + caption + "(" + bean.getClass().getSimpleName() + ") >>> " + from.getClass().getSimpleName());
	}

	public static void registeFilter(Object bean, String caption, Object from) {
		logger.info("注册Filter：" + caption + "(" + bean.getClass().getSimpleName() + ") >>> " + from.getClass().getSimpleName());
	}

	public static void registeServlet(Object bean, String caption, Object from) {
		logger.info("注册Servlet：" + caption + "(" + bean.getClass().getSimpleName() + ") >>> " + from.getClass().getSimpleName());
	}

	public static void registeAdapters(String name, String adaptersCaption, Object from) {
		logger.info("注册Adapters：" + name + "(" + adaptersCaption + ") >>> " + from.getClass().getSimpleName());
	}
	
	public static void registeOther(String name, String caption, Object from) {
		logger.info("注册 ：" + name + "(" + caption + ") >>> " + from.getClass().getSimpleName());
	}

}
