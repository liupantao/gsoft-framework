package com.gsoft.framework.remote.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;

import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.core.dataobj.Record;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.orm.PagerRecords;
import com.gsoft.framework.remote.data.ResContext;
import com.gsoft.framework.remote.method.ServiceDataBinder;
import com.gsoft.framework.util.StringUtils;

/**
 * esb请求返回 工具类
 * 
 * @author liupantao
 * 
 */
public class ResParseUtils {

	/**
	 * 将返回值组装为 clazz
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T getRecordRes(ResContext<?> resContext, Class<T> clazz) {
		validataMsg(resContext);
		Object record = resContext.getRecord();
		return record2Domain(record, clazz);
	}

	/**
	 * 将返回值组装为List<clazz>
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> getRecordsRes(ResContext<?> resContext, Class<T> clazz) {
		validataMsg(resContext);
		List<? extends Domain> records = resContext.getData();
		return records2Domains(records, clazz);
	}

	/**
	 * 将返回值组装为 PagerRecords
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> PagerRecords getPagerRecordsRes(ResContext<?> resContext, Class<T> clazz) {
		validataMsg(resContext);
		List<T> records = getRecordsRes(resContext, clazz);
		PagerRecords pagerRecords = new PagerRecords(records, resContext.getCount());
		return pagerRecords;
	}

	/**
	 * 将返回值组装为 Record
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Record getRecordRes(ResContext<?> resContext) {
		validataMsg(resContext);
		validataMsg(resContext);
		Object record = resContext.getRecord();
		Record retRecord = new Record();
		if (record != null && record instanceof Map) {
			retRecord.putAll((Map) record);
		}
		return retRecord;
	}

	/**
	 * 将map封装为clazz
	 * 
	 * @param <T>
	 * @param record
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T record2Domain(Object record, Class<T> clazz) {
		if (record != null) {
			if (record.getClass().isAssignableFrom(clazz)) {
				return (T) record;
			} else if (record instanceof Map) {
				return (T) map2Domain((Map) record, clazz);
			}
		}
		return null;
	}

	/**
	 * 将List<Map>封装为List<clazz>
	 * 
	 * @param <T>
	 * @param records
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> records2Domains(List<?> records, Class<T> clazz) {
		List<T> domains = new ArrayList<T>();
		if (records != null) {
			for (Object record : records) {
				domains.add(record2Domain(record, clazz));
			}
		}
		return domains;
	}

	/**
	 * 转换Domain对象
	 * 
	 * @param map
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T map2Domain(Map<String, Object> params, T domain) {
		try {
			ServiceDataBinder dataBinder = new ServiceDataBinder(domain);
			Map<String, Object> pValues = new HashMap<String, Object>();
			Map2Values(null, params, pValues);
			PropertyValues pvs = new MutablePropertyValues(pValues);
			dataBinder.bind(pvs);
			return (T) dataBinder.getTarget();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusException("对象转换失败-map2Domain error," + e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void Map2Values(String prefix, Map<String, Object> params, Map<String, Object> pValues) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = StringUtils.isEmpty(prefix) ? entry.getKey() : prefix + "." + entry.getKey();
			if (entry.getValue() == null) {
				continue;
			}
			if (entry.getValue() instanceof List) {
				List<Object> value = (List<Object>) entry.getValue();
				for (int i = 0; i < value.size(); i++) {
					String objKey = key + "[" + i + "]";
					Object obj = value.get(i);
					if (obj != null && obj instanceof Map) {
						Map<String, Object> objMap = (Map) obj;
						Map2Values(objKey, objMap, pValues);
					} else {
						pValues.put(objKey, obj);
					}
				}

			} else if (entry.getValue() instanceof Map) {
				Map2Values(key, (Map) entry.getValue(), pValues);
			} else if (entry.getValue() instanceof String[]) {
				pValues.put(key, (String[]) entry.getValue());
			} else {
				pValues.put(key, entry.getValue());
			}
		}
	}

	/**
	 * 转换Domain对象
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T map2Domain(Map<String, Object> map, Class<T> clazz) {
		T domain = BeanUtils.instantiateClass(clazz);
		return map2Domain(map, domain);
	}
	
	private static void validataMsg(ResContext<?> resContext){
		if(resContext!=null&&resContext.getMsg()!=null){
			throw new BusException(resContext.getCode(),resContext.getMsg());
		}
	}
}
