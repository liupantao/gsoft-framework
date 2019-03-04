package com.gsoft.framework.remote.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gsoft.framework.core.dataobj.Domain;

@Target({ java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainCollection {
	public abstract String name();

	public abstract Class<? extends Domain> domainClazz();
}