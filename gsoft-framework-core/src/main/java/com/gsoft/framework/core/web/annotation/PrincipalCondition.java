/**
 * 
 */
package com.gsoft.framework.core.web.annotation;

import com.gsoft.framework.core.orm.Condition;

/**
 * 用于描述DataDispatchController类
 * 控制映射的DataInDomain里属性的数据权限
 * @author Administrator
 */
public @interface PrincipalCondition {

	String property();

	String operator() default Condition.EQUALS;
	
	String principalProperty();

}
