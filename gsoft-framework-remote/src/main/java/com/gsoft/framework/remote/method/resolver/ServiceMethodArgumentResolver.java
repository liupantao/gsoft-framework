/**
 * 
 */
package com.gsoft.framework.remote.method.resolver;

import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;

public interface ServiceMethodArgumentResolver {

	/**
	 * @param parameter
	 * @return
	 */
	boolean supportsParameter(MethodParameter parameter);
	
	/**
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	Object resolveArgument(MethodParameter parameter,
			Map<String,List<Object>> params,Object... providedArgs) throws Exception;
}
