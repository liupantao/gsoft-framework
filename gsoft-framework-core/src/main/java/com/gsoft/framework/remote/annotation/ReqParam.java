package com.gsoft.framework.remote.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 
 * @author liupantao
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReqParam {
	
	@AliasFor("name")
	String value() default "";
	
	@AliasFor("value")
	String name() default "";
	
	String pubProperty() default "";
	
	boolean required() default true;

}
