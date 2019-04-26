/**
 * 
 */
package com.gsoft.framework.core.orm;

import com.gsoft.framework.core.orm.hibernate.JpaCondition;

/**
 * @author Administrator
 *
 */
public class ConditionFactory {
	private static ConditionFactory conditionFactory = null;
	
	private ConditionFactory(){
		
	}
	
	public static ConditionFactory getInstance(){
		if(conditionFactory==null){
			conditionFactory = new ConditionFactory();
		}
		return conditionFactory;
	}
	
	public Condition getCondition(String property,String operator,Object value){
		return new JpaCondition(property, operator, value);
	}
	
	public Order getOrder(String propertyName, boolean ascending){
		return new Order(propertyName,ascending);
	}
}
