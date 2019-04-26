/**
 * 
 */
package com.gsoft.framework.remote.method.resolver;

import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;

import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.remote.annotation.ServiceMapping;
import com.gsoft.framework.remote.method.DomainMappingUtils;
import com.gsoft.framework.remote.method.ServiceDataBinder;

public class DomainServiceMethodArgumentResolver implements ServiceMethodArgumentResolver {

	public boolean supportsParameter(MethodParameter parameter) {
		return Domain.class.isAssignableFrom(parameter.getParameterType());
	}

	public Object resolveArgument(MethodParameter parameter, Map<String, List<Object>> params, Object... providedArgs)
			throws Exception {

		ServiceDataBinder dataBinder = DomainMappingUtils.getMappingDomainBinder(parameter.getParameterType(), params,
				parameter.getMethodAnnotation(ServiceMapping.class), providedArgs);

		return dataBinder.getTarget();
	}

}
