/**
 * 
 */
package com.gsoft.framework.remote.method.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;

import com.gsoft.framework.remote.data.ReqParamMap;

/**
 * ReqParamMapServiceMethodArgumentResolver
 * 
 * @author liupantao
 * @date 2018年5月21日
 * 
 */
public class ReqParamMapServiceMethodArgumentResolver implements ServiceMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return ReqParamMap.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, Map<String, List<Object>> params, Object... providedArgs)
			throws Exception {

		ReqParamMap mapParams = new ReqParamMap();

		for (Map.Entry<String, List<Object>> entry : params.entrySet()) {
			List<Object> values = entry.getValue();
			if (values == null) {
				continue;
			} else {
				List<String> sValues = new ArrayList<>();
				for (Object value : values) {
					if (value != null) {
						sValues.add(value.toString());
					}
				}
				mapParams.put(entry.getKey(), sValues);
			}
		}
		return mapParams;
	}

}
