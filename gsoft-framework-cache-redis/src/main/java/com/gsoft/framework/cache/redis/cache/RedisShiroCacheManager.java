package com.gsoft.framework.cache.redis.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 缓存
 * 
 * @author LiuPeng
 * 
 */
public class RedisShiroCacheManager implements CacheManager {

	private RedisTemplate<Object, Object> redisTemplate;

	private String applicationName;

	private long expiration = 1800;

	public RedisShiroCacheManager(RedisTemplate<Object, Object> redisTemplate, String applacationName) {
		this.redisTemplate = redisTemplate;
		this.applicationName = applacationName;
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		name = this.applicationName + ":" + name;
		return new RedisShiroCache<K, V>(name, redisTemplate, expiration);
	}

	public RedisTemplate<Object, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public long getExpiration() {
		return expiration;
	}

	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

}