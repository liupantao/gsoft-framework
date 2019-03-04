/*
 * Copyright 2012 the original author or authors.
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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationUtils;
import org.springframework.util.Assert;

import com.gsoft.framework.context.annotation.ModuleScannerRegistrar;

/**
 * jpaDao自动注入
 * 
 * @author liupantao
 * 
 */
class JpaDaosRegistrar extends ModuleScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware,
		EnvironmentAware {

	private Log logger = LogFactory.getLog(JpaDaosRegistrar.class);
	private ResourceLoader resourceLoader;
	private Environment environment;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ResourceLoaderAware#setResourceLoader(org
	 * .springframework.core.io.ResourceLoader)
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.EnvironmentAware#setEnvironment(org.
	 * springframework.core.env.Environment)
	 */
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.annotation.ImportBeanDefinitionRegistrar#
	 * registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata,
	 * org.springframework.beans.factory.support.BeanDefinitionRegistry)
	 */
	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

		super.registerBeanDefinitions(annotationMetadata, registry);

		Assert.notNull(resourceLoader, "ResourceLoader must not be null!");
		Assert.notNull(annotationMetadata, "AnnotationMetadata must not be null!");
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null!");

		// Guard against calls for sub-classes
		if (annotationMetadata.getAnnotationAttributes(getAnnotation().getName()) == null) {
			return;
		}

		AnnotationDaoConfigurationSource configurationSource = new AnnotationDaoConfigurationSource(annotationMetadata,
				getAnnotation(), resourceLoader, environment, registry);

		RepositoryConfigurationExtension extension = getExtension();
		RepositoryConfigurationUtils.exposeRegistration(extension, registry, configurationSource);

		RepositoryConfigurationDelegate delegate = new RepositoryConfigurationDelegate(configurationSource,
				resourceLoader, environment);

		List<BeanComponentDefinition> beanComponentDefinitions = delegate.registerRepositoriesIn(registry, extension);
		logger.info("jpaDao自动注入完成: " + configurationSource.getEntityManagerFactory() + "["
				+ beanComponentDefinitions.size() + "] 个");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.config.
	 * RepositoryBeanDefinitionRegistrarSupport#getAnnotation()
	 */
	protected Class<? extends Annotation> getAnnotation() {
		return EnableJpaDaos.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.config.
	 * RepositoryBeanDefinitionRegistrarSupport#getExtension()
	 */
	protected RepositoryConfigurationExtension getExtension() {
		return new JpaRepositoryConfigExtension();
	}
}
