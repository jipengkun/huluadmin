package com.hulu.util;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public final class RedisUtil {
	private Logger logger = Logger.getLogger(RedisUtil.class);
	
	@Resource(name="redisTemplate")
	private RedisTemplate<Serializable, Object> redisTemplate;

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
		Object result = null;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
			result = operations.get(key);
		} catch (Exception e) {
			logger.error("读取Redis缓存发生错误！", e);
		}
		return result;
	}

	/**
	 * 读取缓存
	 *
	 * @param key
	 * @return
	 */
	public Object getJson(final String key, Class clazz) {
		Object result = this.get(key);
		if(result != null){
			try {
				return JsonUtils.jsonToObject(result.toString(), clazz);
			} catch (Exception e) {
				logger.error("将读取的Redis缓存数据转换为对象发生错误！", e);
			}
		}
		return result;
	}

	/**
	 * 读取缓存
	 *
	 * @param key
	 * @return
	 */
	public Object getJsonList(final String key, TypeReference tr) {
		Object result = this.get(key);
		if(result != null){
			try {
				return JsonUtils.jsonToGenericObject(result.toString(), tr);
			} catch (Exception e) {
				logger.error("将读取的Redis缓存数据转换为对象发生错误！", e);
			}
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			String jsonValueStr = JsonUtils.ObjtoJson(value);
			result = this.set(key, jsonValueStr);
		} catch (Exception e) {
			logger.error("写入Redis缓存发生错误！", e);
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			String jsonValueStr = JsonUtils.ObjtoJson(value);
			result = this.set(key, jsonValueStr, expireTime);
		} catch (Exception e) {
			logger.error("写入Redis缓存发生错误！", e);
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, String value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			logger.error("写入Redis缓存发生错误！", e);
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, String value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			logger.error("写入Redis缓存发生错误！", e);
		}
		return result;
	}

	public void setRedisTemplate(
			RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}