/**
 * 
 */
package com.gsoft.framework.remote.method.resolver;

import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;

import com.gsoft.framework.remote.data.PubContext;


public class PubContextServiceMethodArgumentResolver implements ServiceMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return PubContext.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			Map<String, List<Object>> params, Object... providedArgs)
			throws Exception {
		for(Object providedArg:providedArgs){
			if(providedArg instanceof PubContext){
				return (PubContext)providedArg;
			}
		}
		return null;
	}

}
