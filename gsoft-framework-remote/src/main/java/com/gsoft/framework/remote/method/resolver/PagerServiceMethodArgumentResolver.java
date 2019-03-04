package com.gsoft.framework.remote.method.resolver;

import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;

import com.gsoft.framework.core.orm.Pager;

public class PagerServiceMethodArgumentResolver implements ServiceMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Pager.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, Map<String, List<Object>> params, Object... providedArgs)
			throws Exception {
		String pageSize = getString(params, "limit");
		String pageIndex = getString(params, "page");
		return new Pager(pageIndex, pageSize);
	}

	public String getString(Map<String, List<Object>> params, String paramName) {
		List<Object> values = params.get(paramName);
		if (values == null || values.size() == 0) {
			return "";
		}
		return values.get(0).toString();

	}
}
