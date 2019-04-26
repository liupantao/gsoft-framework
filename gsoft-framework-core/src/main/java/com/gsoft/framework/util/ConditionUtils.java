/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gsoft.framework.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.persistence.Transient;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;

import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.core.orm.Condition;
import com.gsoft.framework.core.orm.ConditionFactory;
import com.gsoft.framework.core.orm.Order;
import com.gsoft.framework.core.orm.hibernate.JpaCondition;
import com.gsoft.framework.core.orm.hibernate.JpaOperatorCondition;

/**
 * 条件Condition工具类
 * @author liupantao
 * @date 2017年10月17日
 *  
 */
public class ConditionUtils {

	/**
	 * 组装查询条件
	 * 
	 * @param prefix
	 * @param bean
	 * @param result
	 * @param filterMap
	 * @param parameterMap
	 *            request中的原始参数
	 * @return
	 */
	public static Collection<Condition> getConditions(String prefix, Domain bean, BindingResult result,
			Map<String, String> filterMap, Map<String, String[]> parameterMap) {
		Collection<Condition> conditions = new ArrayList<Condition>();
		if (prefix != null) {
			prefix += ".";
		} else {
			prefix = "";
		}
		PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(bean.getClass());
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String name = propertyDescriptor.getName();
			if ("class".equals(name)) {
				continue;
			}
			String propertyName = prefix + name;

			Transient tsi = propertyDescriptor.getReadMethod().getAnnotation(Transient.class);
			if (tsi != null) {
				continue;// 非数据库映射字段直接跳过
			}

			Object value = result.getFieldValue(propertyName);
			String operator = filterMap.get(propertyName);
			if (operator == null) {
				operator = Condition.EQUALS;
			}
			if (value != null && !value.equals("")) {
				if (value instanceof Domain) {
					conditions.addAll(getConditions(propertyName, (Domain) value, result, filterMap, parameterMap));
				} else if (value instanceof Set) {
					// TODO 集合类型
				} else if (value instanceof Collection) {
					// TODO
				} else if (value instanceof Map) {
					// 加入map的条件，map条件也使用 . 作为分隔符,目前只支持string类型的映射。
					for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
						if (entry.getKey().startsWith(prefix + propertyName + ".") && entry.getValue().length > 0) {
							conditions.add(ConditionFactory.getInstance().getCondition(entry.getKey(), operator,
									entry.getValue()[0]));
						}
					}
				} else {// 基本类型
					// 不在parameterMap中的直接跳过
					if (!parameterMap.containsKey(propertyName)) {
						continue;
					}
					conditions.add(ConditionFactory.getInstance().getCondition(propertyName, operator, value));
				}
			}
		}
		return conditions;
	}

	/**
	 * 组装查询条件对象
	 * 
	 * @param propertyName
	 * @param operator
	 * @param value
	 * @return
	 */
	public static Condition getCondition(String propertyName, String operator, Object value) {
		return new JpaCondition(propertyName, operator, value);
	}

	/**
	 * 组装排序对象
	 * 
	 * @param propertyName
	 * @param ascending
	 * @return
	 */
	public static Order getOrder(String propertyName, boolean ascending) {
		return new Order(propertyName, ascending);
	}

	/**
	 * 组装OR查询条件对象
	 * 
	 * @param cond1
	 * @param cond2
	 * @return
	 */
	public static JpaOperatorCondition getAndCondition(Condition... conditions) {
		return new JpaOperatorCondition(JpaOperatorCondition.O_AND,conditions);
	}
	
	/**
	 * 组装OR查询条件对象
	 * 
	 * @param cond1
	 * @param cond2
	 * @return
	 */
	public static JpaOperatorCondition getOrCondition(Condition... conditions) {
		return new JpaOperatorCondition(JpaOperatorCondition.O_OR,conditions);
	}
}
