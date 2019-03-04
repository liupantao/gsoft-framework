package com.gsoft.framework.cache.redis.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration; 
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.gsoft.framework.cache.redis.cache.RedisShiroCacheManager;
import com.gsoft.framework.cache.redis.serializer.RedisObjectSerializer;
import com.gsoft.framework.core.log.ConfigRegisterLog;  

import java.time.Duration; 

/**
 * redis缓存
 * 
 * @author liupantao
 * 
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {
	
	@Value("${cache.redis.expriation:1800}")
	private long expriation;
	
	@Value("${spring.application.name}")
	private String applicationName;


	/*　@Bean
	@Primary
	public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		cacheManager.setUsePrefix(true);
		cacheManager.setDefaultExpiration(expriation);
		ConfigRegisterLog.registeBean(cacheManager, "Redis缓存Manager", this);
		return cacheManager;
	}*/
	@Bean
	@Primary
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
		
		return RedisCacheManager 
				.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
				.cacheDefaults(redisCacheConfiguration).build();
	}
  
	
	@Bean
	@Primary
	public RedisShiroCacheManager redisShiroCacheManager(RedisTemplate<Object, Object> redisTemplate) {
		RedisShiroCacheManager cacheManager = new RedisShiroCacheManager(redisTemplate,applicationName);
		cacheManager.setExpiration(expriation);
		ConfigRegisterLog.registeBean(cacheManager, "RedisShiro缓存Manager", this);
		return cacheManager;
	}
	


}