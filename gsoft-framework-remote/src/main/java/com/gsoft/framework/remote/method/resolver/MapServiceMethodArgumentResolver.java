/**
 * 
 */
package com.gsoft.framework.remote.method.resolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.AttributeList;

import org.springframework.core.MethodParameter;

public class MapServiceMethodArgumentResolver implements ServiceMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Map.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, Map<String, List<Object>> params, Object... providedArgs)
			throws Exception {

		Map<String, Object> mapParams = new HashMap<String, Object>();

		for (Map.Entry<String, List<Object>> entry : params.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			} else if (entry.getValue() instanceof AttributeList) {
				mapParams.put(entry.getKey(), entry.getValue().get(0));
			} else {
				mapParams.put(entry.getKey(), entry.getValue());
			}
		}
		return mapParams;
	}

}
