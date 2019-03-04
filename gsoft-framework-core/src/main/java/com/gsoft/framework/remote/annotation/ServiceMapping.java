/**
 * 
 */
package com.gsoft.framework.remote.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gsoft.framework.core.web.annotation.Filter;

/**
 * 
 * @author liupantao
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceMapping {
	
	InitializeProperty[] initializeProperties() default {};

	PubCondition[] pubConditions() default {};

	Filter[] filters() default {};

	String caption() default "";// 交易描述

	String trancode() default "";// 交易代码

	boolean log() default false;// 日志
}