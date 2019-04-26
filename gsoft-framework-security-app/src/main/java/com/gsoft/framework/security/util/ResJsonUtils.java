package com.gsoft.framework.security.util;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;
import com.gsoft.framework.core.web.view.Message;
import com.gsoft.framework.security.AppResConstants;
import com.gsoft.framework.security.authc.AppResContext;

/**
 * 返回json工具类
 * 
 * @author liupantao
 *
 */
public class ResJsonUtils {

	private static Log logger = LogFactory.getLog(ResJsonUtils.class);

	public static void resJson(ServletRequest request, ServletResponse response, Object obj) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			out = response.getWriter();
			out.print(JSON.toJSONString(obj));
			out.flush();
		} catch (Exception e) {
			logger.error("ApiGateway auth res error," + e.getMessage(), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	/**
	 * 未登录
	 * @param request
	 * @param response
	 */
	public static void resTokenError(ServletRequest request, ServletResponse response) {
		AppResContext context = new AppResContext();
		context.setMessage(new Message(AppResConstants.ERROR_TOKEN, "token认证失败."));
		resJson(request, response, context);
	}

}
