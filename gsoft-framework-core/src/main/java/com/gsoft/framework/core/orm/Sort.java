package com.gsoft.framework.core.orm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 排序
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class Sort extends org.springframework.data.domain.Sort {

	/**
	 * 
	 */
	private static final long serialVersionUID = -361019140263314410L;

	/**
	 * Creates a new {@link Sort} instance using the given {@link Order}s.
	 * 
	 * @param orders
	 *            must not be {@literal null}.
	 */
	public Sort(Order... orders) {
		super(orders);
	}

	/**
	 * Creates a new {@link Sort} instance.
	 * 
	 * @param orders
	 *            must not be {@literal null} or contain {@literal null}.
	 */
	public Sort(List<Order> orders) {
		super(orders);
	}

	/**
	 * Creates a new {@link Sort} instance. Order defaults to
	 * {@value Direction#ASC}.
	 * 
	 * @param properties
	 *            must not be {@literal null} or contain {@literal null} or
	 *            empty strings
	 */
	public Sort(String... properties) {
		super(DEFAULT_DIRECTION, properties);
	}

	/**
	 * Creates a new {@link Sort} instance.
	 * 
	 * @param direction
	 *            defaults to {@linke Sort#DEFAULT_DIRECTION} (for
	 *            {@literal null} cases, too)
	 * @param properties
	 *            must not be {@literal null}, empty or contain {@literal null}
	 *            or empty strings.
	 */
	public Sort(boolean ascending, String... properties) {
		super(ascending ? Direction.ASC : Direction.DESC, properties == null ? new ArrayList<String>() : Arrays
				.asList(properties));
	}

	/**
	 * Creates a new {@link Sort} instance.
	 * 
	 * @param direction
	 *            defaults to {@linke Sort#DEFAULT_DIRECTION} (for
	 *            {@literal null} cases, too)
	 * @param properties
	 *            must not be {@literal null} or contain {@literal null} or
	 *            empty strings.
	 */
	public Sort(boolean ascending, List<String> properties) {
		super(ascending ? Direction.ASC : Direction.DESC, properties);
	}
}
