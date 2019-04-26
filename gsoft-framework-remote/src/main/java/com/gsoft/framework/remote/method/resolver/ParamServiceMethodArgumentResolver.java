/**
 * 
 */
package com.gsoft.framework.remote.method.resolver;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;

import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.remote.annotation.ServiceParam;
import com.gsoft.framework.remote.data.PubContext;
import com.gsoft.framework.util.StringUtils;

/**
 * @ServiceParam 解析
 * @author liupantao
 * @date 2017年10月30日
 * 
 */
public class ParamServiceMethodArgumentResolver implements ServiceMethodArgumentResolver {

	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(ServiceParam.class) != null;
	}

	@SuppressWarnings("unchecked")
	public Object resolveArgument(MethodParameter parameter, Map<String, List<Object>> params, Object... providedArgs)
			throws Exception {
		ServiceParam serviceParam = parameter.getParameterAnnotation(ServiceParam.class);
		String paramName = serviceParam.name();
		List<Object> values = null;

		if (StringUtils.isNotEmpty(paramName)) {
			values = params.get(paramName);
		}
		// 如果为空,
		if (values == null && StringUtils.isNotEmpty(serviceParam.pubProperty())) {
			PubContext pubContext = null;
			for (Object providedArg : providedArgs) {
				// 取出公共头信息对象
				if (providedArg instanceof PubContext) {
					pubContext = (PubContext) providedArg;
				}
			}
			if (pubContext != null) {
				Object pubValue = pubContext.getParams().get(serviceParam.pubProperty());
				if (pubValue != null) {
					if (List.class.isAssignableFrom(pubValue.getClass())) {
						values = (List<Object>) pubValue;
					} else if (pubValue.getClass().isArray()) {
						values = Arrays.asList(pubValue);
					} else {
						return pubValue;
					}
				}
				// getSimplePropertyValue不支持pubContext.params取值
				// return PropertyUtils.getSimplePropertyValue(pubContext,
				// serviceParam.pubProperty());
			}
		}

		if (serviceParam.required() && values == null) {
			throw new BusException(MessageFormat.format("Required parameter \"{0}\" is not present", paramName));
		}

		Class<?> pType = parameter.getParameterType();
		if (List.class.isAssignableFrom(pType)) {
			return values;
		} else if (pType.isArray() && values != null) {
			return values.toArray(new String[values.size()]);
		}
		return values == null || values.size() == 0 ? null : values.get(0);
	}

}
