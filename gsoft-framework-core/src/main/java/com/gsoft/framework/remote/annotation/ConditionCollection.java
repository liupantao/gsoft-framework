package com.gsoft.framework.remote.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gsoft.framework.core.dataobj.Domain;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionCollection {

	Class<? extends Domain> domainClazz() default Domain.class;
	
	String[] customParams() default {};
}
