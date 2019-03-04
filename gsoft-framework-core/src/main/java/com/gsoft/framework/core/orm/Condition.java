/**
 * 
 */
package com.gsoft.framework.core.orm;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * @author Administrator
 *
 */
public interface Condition extends Serializable{
	public static final String BETWEEN = "BETWEEN";
	public static final int BETWEEN_LENTH = 2;
//	public static final String BETWEEN_AND_EQUALS = "BETWEEN_AND_EQUALS";
//	public static final String GREATNESS = "GREATNESS";
//	public static final String SMALLNESS = "SMALLNESS";
//	public static final String GREATNESS_AND_EQUALS = "GREATNESS_AND_EQUALS";
//	public static final String SMALLNESS_AND_EQUALS = "SMALLNESS_AND_EQUALS";
	
	/**
	 * @Fields 模糊查询 like %value%
	 */
	public static final String LIKE = "LIKE";
	/**
	 * @Fields 以{value}开始 like value%
	 */
	public static final String START = "START";
	/**
	 * @Fields 以{value}结束 like %value
	 */
	public static final String END = "END";
	/**
	 * @Fields 等于 =
	 */
	public static final String EQUALS = "EQUALS";
	/**
	 * @Fields 不等于 !=
	 */
	public static final String IS_NOT_NULL = "NOT_NULL";
	/**
	 * @Fields 为空 isnull
	 */
	public static final String IS_NULL = "IS_NULL";
	/**
	 * @Fields 或 or
	 */
	public static final String OR = "OR";
	/**
	 * @Fields IN in
	 */
	public static final String IN = "IN";
	
	/**
	 * @Fields NOT_IN
	 */
	public static final String NOT_IN = "NOT_IN";
	
	/**
	 * @Fields 忽略大小写等于
	 */
	public static final String IGNORE_CASE_EQUALS = "IGNORE_CASE_EQUALS";
	/**
	 * @Fields 忽略大小写like
	 */
	public static final String IGNORE_CASE_LIKE = "IGNORE_CASE_LIKE";
	
	/**
	 * @Fields NOT_EQUALS : 不等于
	 */
	public static final String NOT_EQUALS = "NOT_EQUALS";
	
	/**
	 * @Fields LEFT : 小于  <
	 */
	public static final String LEFT = "LEFT";
	/**
	 * @Fields RIGHT : 大于 >
	 */
	public static final String RIGHT = "RIGHT";
	
	/**
	 * @Fields LEFT_EQ : 小于等于 <= 
	 */
	public static final String LEFT_EQ = "LEFT_EQ";
	/**
	 * @Fields RIGHT_EQ : 大于等于 >=
	 */
	public static final String RIGHT_EQ = "RIGHT_EQ";
	
	/**
	 * @Fields BETWEEN 分隔符
	 */
	public static final String BETWEEN_SPLIT = "-BTW-";
	
	/** 
	 * 获取property
	 * @return 
	 */
	public String getProperty();
	
	/** 
	 * 获取operator
	 * @return 
	 */
	public String getOperator();
	
	/** 
	 * 获取value
	 * @return 
	 */
	public Object getValue();
	
	/** 
	 * 表达式
	 * @param root
	 * @param cb
	 * @return 
	 */
	public Predicate generateExpression(Root<?> root, CriteriaBuilder cb);

}
