package com.gsoft.framework.cache.redis.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.gsoft.framework.cache.redis.cache.RedisShiroCacheManager;
import com.gsoft.framework.cache.redis.serializer.RedisObjectSerializer;
import com.gsoft.framework.core.log.ConfigRegisterLog;

/**
 * redis缓存
 * 
 * @author LiuPeng
 * 
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {
	
	@Value("${cache.redis.expriation:1800}")
	private long expriation;
	
	@Value("${spring.application.name}")
	private String applicationName;
	

	@Bean
	@Primary
	public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		cacheManager.setUsePrefix(true);
		cacheManager.setDefaultExpiration(expriation);
		ConfigRegisterLog.registeBean(cacheManager, "Redis缓存Manager", this);
		return cacheManager;
	}
	
	@Bean
	@Primary
	public RedisShiroCacheManager redisShiroCacheManager(RedisTemplate<Object, Object> redisTemplate) {
		RedisShiroCacheManager cacheManager = new RedisShiroCacheManager(redisTemplate,applicationName);
		cacheManager.setExpiration(expriation);
		ConfigRegisterLog.registeBean(cacheManager, "RedisShiro缓存Manager", this);
		return cacheManager;
	}
	
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new RedisObjectSerializer());
		template.afterPropertiesSet();
		ConfigRegisterLog.registeBean(template, "RedisTemplate", this);
		return template;
	}

}