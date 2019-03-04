/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gsoft.framework.jpa.repository;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;

import com.gsoft.framework.context.ModuleConfig;
import com.gsoft.framework.context.ModuleFactory;

/**
 * Annotation based {@link AnnotationRepositoryConfigurationSource}.
 * 
 * @author liupantao
 */
public class AnnotationDaoConfigurationSource extends AnnotationRepositoryConfigurationSource {

	private static final String ENTITY_MANAGER_FACTORY_REF = "entityManagerFactoryRef";

	private final AnnotationAttributes attributes;

	/**
	 * Creates a new {@link AnnotationDaoConfigurationSource} from the given {@link AnnotationMetadata} and
	 * annotation.
	 * 
	 * @param metadata must not be {@literal null}.
	 * @param annotation must not be {@literal null}.
	 * @param resourceLoader must not be {@literal null}.
	 * @param environment
	 */
	public AnnotationDaoConfigurationSource(AnnotationMetadata metadata, Class<? extends Annotation> annotation,
			ResourceLoader resourceLoader, Environment environment, BeanDefinitionRegistry registry) {

		super(metadata, annotation, resourceLoader, environment, registry);

		this.attributes = new AnnotationAttributes(metadata.getAnnotationAttributes(annotation.getName()));
	}

	public Iterable<String> getBasePackages() {
		String entityManagerFactory = getEntityManagerFactory();
		Set<String> packages = new HashSet<String>();
		Map<String, ModuleConfig> modules = ModuleFactory.getInstance().getModules();
		for (Map.Entry<String, ModuleConfig> entry : modules.entrySet()) {
			if(entityManagerFactory.equals(entry.getValue().getEntityManagerFactory())){
				String basePackage = entry.getValue().getBasePackage();
				String[] daoPackages = entry.getValue().getDaoPackages();
				if (daoPackages != null) {
					for (String daoPackage : daoPackages) {
						packages.add(basePackage + "." + daoPackage);
					}
				}
			}
		}
		return packages;
	}
	
	public String getEntityManagerFactory(){
		return attributes.getString(ENTITY_MANAGER_FACTORY_REF);
	}
}
