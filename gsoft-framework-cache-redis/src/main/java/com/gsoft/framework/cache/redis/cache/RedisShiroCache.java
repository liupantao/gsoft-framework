package com.gsoft.framework.cache.redis.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 
 * @author LiuPeng
 * 
 * @param <K>
 * @param <V>
 */
@SuppressWarnings("unchecked")
public class RedisShiroCache<K, V> implements Cache<K, V> {

	private static final String REDIS_SHIRO_CACHE = "gshiro:";
	private String cacheKey;
	private RedisTemplate<K, V> redisTemplate;
	private long expiration;

	@SuppressWarnings("rawtypes")
	public RedisShiroCache(String name, RedisTemplate client) {
		this(name, client, 30);
	}

	@SuppressWarnings("rawtypes")
	public RedisShiroCache(String name, RedisTemplate client, long expiration) {
		this.cacheKey = REDIS_SHIRO_CACHE + name + ":";
		this.redisTemplate = client;
		this.expiration = expiration;
	}

	@Override
	public V get(K key) throws CacheException {
		try {
			if (key == null) {
				return null;
			} else {
				redisTemplate.boundValueOps(getCacheKey(key)).expire(expiration, TimeUnit.SECONDS);
				return redisTemplate.boundValueOps(getCacheKey(key)).get();
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public V put(K key, V value) throws CacheException {
		try {
			V old = get(key);
			redisTemplate.boundValueOps(getCacheKey(key)).set(value);
			return old;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public V remove(K key) throws CacheException {
		try {
			V old = get(key);
			redisTemplate.delete(getCacheKey(key));
			return old;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public void clear() throws CacheException {
		try {
			redisTemplate.delete(keys());
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public int size() {
		try {
			return keys().size();
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public Set<K> keys() {
		try {
			return redisTemplate.keys(getCacheKey("*"));
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public Collection<V> values() {
		try {
			Set<K> set = keys();
			List<V> list = new ArrayList<V>();
			for (K s : set) {
				list.add(get(s));
			}
			return list;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	private K getCacheKey(Object k) {
		return (K) (this.cacheKey + k);
	}
}