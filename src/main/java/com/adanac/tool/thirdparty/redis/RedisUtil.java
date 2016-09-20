package com.adanac.tool.thirdparty.redis;

import java.util.Map;

import com.adanac.framework.cache.redis.client.impl.MyShardedClient;
import com.adanac.framework.exception.SysException;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.uniconfig.util.StringUtils;
import com.adanac.framework.utils.JsonUtils;

/**
 * redis操作的工具类
 * 
 * @author zhangs
 *
 */
public class RedisUtil {

	private static MyLogger log = MyLoggerFactory.getLogger(RedisUtil.class);

	private int RETRYCOUNT = 3;

	private static final int expireTime = 3600 * 12;// 超时时间
	/**
	 * redis
	 */
	private MyShardedClient redisClient;

	public RedisUtil(MyShardedClient redisClient) {
		this.redisClient = redisClient;
	}

	/**
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		set(key, value, RETRYCOUNT);
	}

	/**
	 * @param key
	 * @param value
	 * @param count
	 */
	private void set(String key, String value, int count) {
		try {
			redisClient.set(key, value);
		} catch (Exception e) {
			log.error("set from redis by key = [" + key + "] retry count =" + count + " fail ,error = ", e);
			// 重试3次
			if (count > 0) {
				set(key, value, --count);
			}
			throw new SysException("redis连接失败", e);
		} finally {
		}
	}

	/**
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return get(key, RETRYCOUNT);
	}

	/**
	 * 
	 * 根据key从redis中获取，获取不到则从数据库中取，同时存放到redis中去
	 * 
	 * @param <T>
	 * @param key
	 * @return
	 */
	public <T> T get(String key, Cacheable<T> cacheable) {
		String value = null;
		try {
			value = get(key, RETRYCOUNT);
		} catch (SysException e) {
			log.error("get from redis fail", e);
			return cacheable.call();
		}
		// 缓存中没有
		if (StringUtils.isEmpty(value)) {
			log.info("from redis is null,key {}", key);
			T t = cacheable.call();
			if (null != t) {
				try {
					redisClient.set(key, JsonUtils.bean2json(t));
					// 设置默认过期时间
					redisClient.expire(key, expireTime);
				} catch (Exception e) {
					log.error("add to cache error", e);
				}
			}
			return t;
		}
		return JsonUtils.json2bean(value, cacheable.clazz);
	}

	/**
	 * @param key
	 * @param count
	 * @return
	 */
	private String get(String key, int count) {
		String value = null;
		try {
			value = redisClient.get(key);
		} catch (Exception e) {
			log.error("get from redis by key = [" + key + "] retry count =" + count + " fail ,error = ", e);
			// 重试3次
			if (count > 0) {
				value = get(key, --count);
			}
			throw new SysException("redis连接失败", e);
		} finally {
		}
		return value;
	}

	/**
	 * @param key
	 * @param value
	 */
	public void hset(String key, String field, String value) {
		hset(key, field, value, RETRYCOUNT);
	}

	/**
	 * @param key
	 * @param value
	 * @param count
	 */
	private void hset(String key, String field, String value, int count) {
		try {
			redisClient.hset(key, field, value);
		} catch (Exception e) {
			log.error("hset from redis by key = [" + key + "] retry count =" + count + " fail ,error = ");
			// 重试3次
			if (count > 0) {
				set(key, value, --count);
			}
			throw new SysException("redis连接失败", e);
		} finally {
		}
	}

	/**
	 * @param key
	 * @return
	 */
	public String hget(String key, String field) {
		return hget(key, field, RETRYCOUNT);
	}

	/**
	 * 
	 * 根据key从redis中获取，获取不到则从数据库中取，同时存放到redis中去
	 * 
	 * @param <T>
	 * @param key
	 * @return
	 */
	public <T> T hget(String key, String field, Cacheable<T> cacheable) {
		String value = null;
		try {
			value = hget(key, field, RETRYCOUNT);
		} catch (SysException e) {
			log.error("get from redis fail", e);
			return cacheable.call();
		}
		// 缓存中没有
		if (StringUtils.isEmpty(value)) {
			log.info("from redis is null,key,field {}", key + "," + field);
			T t = cacheable.call();
			if (null != t) {
				try {
					redisClient.hset(key, field, JsonUtils.bean2json(t));
					// 设置默认过期时间
					redisClient.expire(key, expireTime);
				} catch (Exception e) {
					log.error("add to cache error", e);
				}
			}
			return t;
		}
		return JsonUtils.json2bean(value, cacheable.clazz);
	}

	/**
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		Map<String, String> value = null;
		try {
			value = redisClient.hgetAll(key);
		} catch (Exception e) {
			log.error("hgetAll from redis by key = [" + key + "] fail ,error = ", e);
		} finally {
		}
		return value;
	}

	/**
	 * @param key
	 * @param count
	 * @return
	 */
	private String hget(String key, String field, int count) {
		String value = null;
		try {
			value = redisClient.hget(key, field);
		} catch (Exception e) {
			log.error("hget from redis by key = [" + key + "] retry count =" + count + " fail ,error = ", e);
		} finally {
		}
		return value;
	}

	/**
	 * 返回和移除列表的第一个元素
	 * 
	 * @param key
	 * @return
	 */
	public String lpop(String key) {
		return lpop(key, RETRYCOUNT);
	}

	/**
	 * 返回和移除列表的第一个元素
	 * 
	 * @param key
	 * @param retrycount
	 *            重试次数
	 * @return
	 */
	private String lpop(String key, int retrycount) {
		String value = null;
		try {
			value = redisClient.lpop(key);
		} catch (Exception e) {
			log.error("rpop from redis by key = [" + key + "] retry count =" + retrycount + " fail ,error = ");
			if (retrycount > 0) {
				value = lpop(key, --retrycount);
			}
		} finally {
		}
		return value;
	}

	/**
	 * 由列表尾部添加字符串值。如果不存在该键则创建该列表。如果该键存在，而且不是一个列表，返回FALSEsss
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public void rpush(String key, String value) {
		rpush(key, value, RETRYCOUNT);
	}

	/**
	 * 由列表尾部添加字符串值。如果不存在该键则创建该列表。如果该键存在，而且不是一个列表，返回FALSE
	 * 
	 * @param key
	 * @param value
	 * @param retrycount
	 *            重试次数
	 * @return
	 */
	private void rpush(String key, String value, int count) {
		try {
			redisClient.rpush(key, value);
		} catch (Exception e) {
			log.error("lpush to redis by key = [" + key + "] value =[" + value + "]retry count =" + count
					+ " fail ,error = ", e);
			if (count > 0) {
				rpush(key, value, --count);
			}
			throw new SysException("redis连接失败", e);
		} finally {
		}
	}

	public void flush() {
		redisClient.flushDB();
	}

	/**
	 * @param key
	 * @param value
	 */
	public void del(String key) {
		try {
			redisClient.del(key);
		} catch (Exception e) {
			throw new SysException("redis连接失败", e);
		}
	}

	/**
	 * @param key
	 * @param field
	 */
	public void hdel(String key, String field) {
		try {
			redisClient.hdel(key, field);
		} catch (Exception e) {
			throw new SysException("redis连接失败", e);
		}
	}

}
