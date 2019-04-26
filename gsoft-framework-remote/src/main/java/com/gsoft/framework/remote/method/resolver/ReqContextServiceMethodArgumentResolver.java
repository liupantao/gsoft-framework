/**
 * 
 */
package com.gsoft.framework.remote.method.resolver;

import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;

import com.gsoft.framework.remote.data.ReqContext;

public class ReqContextServiceMethodArgumentResolver implements
		ServiceMethodArgumentResolver {

	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return ReqContext.class.isAssignableFrom(parameter.getParameterType());
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, java.util.Map, java.lang.Object[])
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			Map<String, List<Object>> params, Object... providedArgs)
			throws Exception {
		if(params instanceof ReqContext){
			return params;
		}
		return null;
	}

}
