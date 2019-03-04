/**
 * 
 */
package com.gsoft.framework.remote.method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;

import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.remote.annotation.PubCondition;
import com.gsoft.framework.remote.annotation.ServiceMapping;
import com.gsoft.framework.remote.data.PubContext;
import com.gsoft.framework.util.BeanUtils;
import com.gsoft.framework.util.PropertyUtils;

public class DomainMappingUtils {

	private static Log logger = LogFactory.getLog(DomainMappingUtils.class);

	private final static String DOMAIN_PROP_SPLIT_KEY = "].";

	/**
	 * @param domainClazz
	 * @param params
	 * @param serviceMapping
	 * @param providedArgs
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ServiceDataBinder getMappingDomainBinder(Class domainClazz, Map<String, List<Object>> params,
			ServiceMapping serviceMapping, Object... providedArgs) {
		Object target = BeanUtils.instantiateClass(domainClazz);
		ServiceDataBinder dataBinder = new ServiceDataBinder(target);

		Map<String, String[]> pValues = new HashMap<String, String[]>();
		for (Map.Entry<String, List<Object>> entry : params.entrySet()) {
			try {
				pValues.put(entry.getKey(), entry.getValue().toArray(new String[entry.getValue().size()]));
			} catch (Exception e) {
				logger.error("remote参数转换异常..." + entry.getKey() + " = " + entry.getValue(), e);
			}
		}

		// 通过公共头注解设置
		if (serviceMapping != null) {
			PubContext putContext = null;
			for (Object providedArg : providedArgs) {
				// 取出公共头信息对象
				if (providedArg instanceof PubContext) {
					putContext = (PubContext) providedArg;
				}
			}
			// 根据公共头映射设置自动写对象值
			if (putContext != null && serviceMapping.pubConditions().length > 0) {
				for (PubCondition pubCondition : serviceMapping.pubConditions()) {
					Object value = PropertyUtils.getPropertyValue(putContext, pubCondition.pubProperty());
					if (value != null) {
						pValues.put(pubCondition.property(), new String[] { value.toString() });
					}
				}
			}
		}

		PropertyValues pvs = new MutablePropertyValues(pValues);
		dataBinder.bind(pvs);
		return dataBinder;
	}

	/**
	 * 
	 * @param propName
	 * @param domainClazz
	 * @param params
	 * @param serviceMapping
	 * @param providedArgs
	 * @return
	 */
	public static Object getMappingDomainsBinder(String propName, Class<? extends Domain> domainClazz,
			Map<String, List<Object>> params, ServiceMapping serviceMapping, Object[] providedArgs) {

		Map<String, DomainMapping> domainMappings = new HashMap<String, DomainMapping>();

		Collection<Object> records = new ArrayList<Object>();

		for (Map.Entry<String, List<Object>> entry : params.entrySet()) {
			String paramName = entry.getKey();
			int splitIndex = paramName.indexOf(DOMAIN_PROP_SPLIT_KEY);
			if (splitIndex > 0 && paramName.startsWith(propName + "[")) {
				String key = paramName.substring(0, splitIndex + DOMAIN_PROP_SPLIT_KEY.length());
				String recordPropName = paramName.substring(splitIndex + DOMAIN_PROP_SPLIT_KEY.length());

				DomainMapping domainMapping;
				if (domainMappings.containsKey(key)) {
					domainMapping = domainMappings.get(key);
				} else {
					domainMapping = new DomainMapping();
				}

				domainMapping.addPropValue(recordPropName, entry.getValue());
				domainMappings.put(key, domainMapping);
			}
		}

		if (domainMappings.size() > 0) {
			Object record;
			for (DomainMapping domainMapping : domainMappings.values()) {
				record = getMappingDomainBinder(domainClazz, domainMapping.getParams(), serviceMapping, providedArgs).getTarget();
				if (record != null) {
					records.add(record);
				}
				record = null;
			}
		}

		return records;
	}

	private static class DomainMapping {

		private Map<String, List<Object>> params = new HashMap<String, List<Object>>();

		void addPropValue(String propName, List<Object> values) {
			params.put(propName, values);
		}

		public Map<String, List<Object>> getParams() {
			return params;
		}
	}
}
