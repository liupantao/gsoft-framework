package com.gsoft.framework.remote.annotation;

import com.gsoft.framework.core.orm.Condition;

/**
 * 
 * @author liupantao
 *
 */
public @interface PubCondition {
	
	String property();

	String operator() default Condition.EQUALS;
	
	String pubProperty();
}
