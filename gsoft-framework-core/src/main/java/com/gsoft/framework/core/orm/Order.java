/**
 * 
 */
package com.gsoft.framework.core.orm;

import org.springframework.data.domain.Sort.Direction;

import com.gsoft.framework.util.StringUtils;

/**
 * 
 * @author liupantao
 *
 */
public class Order extends org.springframework.data.domain.Sort.Order {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3662364516745514267L;

	public Order(String propertyName, boolean ascending) {
		super(ascending ? Direction.ASC : Direction.DESC, StringUtils.isEmpty(propertyName) ? "-" : propertyName);
	}
}
