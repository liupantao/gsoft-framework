/**
 * 
 */
package com.gsoft.framework.core.orm.hibernate;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.orm.Condition;

/**
 * jpa 查询条件
 * 
 * @author Administrator
 * 
 */
public class JpaCondition implements Condition {

	private static final long serialVersionUID = -5265327503901015476L;

	private static final String PROPERTY_SPLIT = ".";

	/**
	 * @Fields 查询时最大解析层数,特殊情况可修改
	 */
	public static int MAX_QUERY_JOIN_COUNT = 3;

	protected String property;
	protected String operator;
	protected Object value;

	/**
	 * 构造函数
	 * 
	 * @param property
	 * @param operator
	 * @param value
	 */
	public JpaCondition(String property, String operator, Object value) {
		this.property = property;
		this.operator = operator;
		this.value = value;
	}

	/**
	 * 生成查询表达式
	 * 
	 * @param alias
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public Predicate generateExpression(Root<?> root, CriteriaBuilder cb) {
		Expression<?> exp = getExpression(root);
		if (value != null) {
			if (this.operator.equals(Condition.EQUALS)) {
				return cb.equal(exp, value);
			}
			if (this.operator.equals(Condition.NOT_EQUALS)) {
				return cb.notEqual(exp, value);
			} else if (this.operator.equals(Condition.LIKE)) {
				return cb.like((Expression<String>) exp, "%" + value.toString() + "%");
			} else if (this.operator.equals(Condition.END)) {
				return cb.like((Expression<String>) exp, "%" + value.toString());
			} else if (this.operator.equals(Condition.START)) {
				return cb.like((Expression<String>) exp, value.toString() + "%");
			} else if (this.operator.equals(Condition.BETWEEN)) {
				String[] betweenArray = value.toString().split(Condition.BETWEEN_SPLIT);
				if (betweenArray.length < Condition.BETWEEN_LENTH) {
					return null;
				}
				return cb.between((Expression<String>) exp, betweenArray[0], betweenArray[1]);
			} else if (this.operator.equals(Condition.IN)) {
				if (value instanceof Object[]) {
					return exp.in((Object[]) value);
				}
			} else if (this.operator.equals(Condition.NOT_IN)) {
				if (value instanceof Object[]) {
					cb.not(exp.in((Object[]) value));
				}
			} else if (this.operator.equals(Condition.LEFT)) {
				// 小于
				return cb.lessThan((Expression<String>) exp, value.toString());
			} else if (this.operator.equals(Condition.RIGHT)) {
				// 大于
				return cb.greaterThan((Expression<String>) exp, value.toString());
			} else if (this.operator.equals(Condition.LEFT_EQ)) {
				// 小于等于
				return cb.lessThanOrEqualTo((Expression<String>) exp, value.toString());
			} else if (this.operator.equals(Condition.RIGHT_EQ)) {
				// 大于等于
				return cb.greaterThanOrEqualTo((Expression<String>) exp, value.toString());
			} else if (this.operator.equals(Condition.IGNORE_CASE_EQUALS)) {
				// 忽略大小写等于
				return cb.equal(cb.upper((Expression<String>) exp), value.toString().toUpperCase());
			} else if (this.operator.equals(Condition.IGNORE_CASE_LIKE)) {
				// 忽略大小写like
				return cb.like(cb.upper((Expression<String>) exp), "%" + value.toString().toUpperCase() + "%");
			}
		}

		if (this.operator.equals(Condition.IS_NULL)) {
			return exp.isNull();
		} else if (this.operator.equals(Condition.IS_NOT_NULL)) {
			return exp.isNotNull();
		}
		return null;
	}

	private Expression<?> getExpression(Root<?> root) {
		String propertyName = getProperty();
		// 处理多对多关系- xxx.xxx.xxx.xxx
		if (propertyName.indexOf(PROPERTY_SPLIT) != -1) {
			int joinCount = propertyName.split("\\.").length;
			if (joinCount > MAX_QUERY_JOIN_COUNT) {
				throw new BusException("查询参数[" + propertyName + "]关联层数不得大于 " + MAX_QUERY_JOIN_COUNT );
			}
		}
		return getJoinExpression(root, propertyName);
	}

	/**
	 * 多层关联解析
	 * 
	 * @param join
	 * @param propertyName
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Expression<?> getJoinExpression(From from, String propertyName) {
		Expression<?> exp = null;
		if (propertyName.indexOf(PROPERTY_SPLIT) != -1) {
			String alias = propertyName.split("\\.")[0];
			String proName = propertyName.substring(alias.length() + 1);
			Set<?> joinSet = from.getJoins();
			Iterator<?> i = joinSet.iterator();
			while (i.hasNext()) {
				Object next = i.next();
				if (next instanceof Join) {
					Join<Object, Object> newJoin = (Join) next;
					if (alias.equals(newJoin.getAlias())) {
						exp = getJoinExpression(newJoin, proName);
						break;
					}
				}
			}
			// 关联查询
			if (exp == null) {
				Join<Object, Object> newJoin = from.join(alias, JoinType.LEFT);
				newJoin.alias(alias);
				exp = getJoinExpression(newJoin, proName);
			}
		} else {
			exp = from.get(propertyName);
		}
		return exp;
	}

	@Override
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Override
	public String toString() {
		return property + " " + operator + " " + value;
	}
}
