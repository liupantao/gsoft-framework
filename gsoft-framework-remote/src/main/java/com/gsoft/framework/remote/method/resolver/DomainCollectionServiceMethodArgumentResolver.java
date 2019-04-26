/**
 * 
 */
package com.gsoft.framework.remote.method.resolver;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;

import com.gsoft.framework.remote.annotation.DomainCollection;
import com.gsoft.framework.remote.annotation.ServiceMapping;
import com.gsoft.framework.remote.method.DomainMappingUtils;

/**
 * 
 * @author liupantao
 *
 */
public class DomainCollectionServiceMethodArgumentResolver implements ServiceMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Collection.class.isAssignableFrom(parameter.getParameterType())
				&& parameter.hasParameterAnnotation(DomainCollection.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, Map<String, List<Object>> params, Object... providedArgs)
			throws Exception {

		DomainCollection domainCollection = parameter.getParameterAnnotation(DomainCollection.class);

		if (domainCollection == null) {
			return null;
		}

		return DomainMappingUtils.getMappingDomainsBinder(domainCollection.name(), domainCollection.domainClazz(), params,
				parameter.getMethodAnnotation(ServiceMapping.class), providedArgs);
	}

}
