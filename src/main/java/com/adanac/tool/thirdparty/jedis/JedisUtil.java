package com.adanac.tool.thirdparty.jedis;

import org.apache.commons.lang.StringUtils;

import com.adanac.tool.j2se.serialization.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtil {

	public static Jedis createJedis() {
		Jedis jedis = new Jedis("127.0.0.1");
		return jedis;
	}

	public static Jedis createJedis(String host, int port) {
		Jedis jedis = new Jedis(host, port);

		return jedis;
	}

	public static Jedis createJedis(String host, int port, String passwrod) {
		Jedis jedis = new Jedis(host, port);

		if (!StringUtils.isNotBlank(passwrod))
			jedis.auth(passwrod);

		return jedis;
	}

	public static void lpush(JedisPool jedisPool, String key, Object value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.lpush(key.getBytes(), SerializeUtil.serialize(value));
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public static Object rpop(JedisPool jedisPool, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return SerializeUtil.unserialize(jedis.rpop(key.getBytes()));
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}