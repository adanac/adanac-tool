package com.adanac.tool.thirdparty.jedis;

import java.util.List;
import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisDemo {
	private static JedisPool pool;

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		if (bundle == null) {
			throw new IllegalArgumentException("[redis.properties] is not found!");
		}
		JedisPoolConfig config = new JedisPoolConfig();
		// config.setMaxActive(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
		config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
		// config.setMaxWait(Long.valueOf(bundle.getString("redis.pool.maxWait")));
		config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));
		pool = new JedisPool(config, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port")));
	}

	public void test() {
		Jedis redis = new Jedis("172.0.0.1", 6379);// 连接redis
		redis.auth("redis");// 验证密码,如果需要验证的话
		// STRING 操作

		// SET key value将字符串值value关联到key。
		redis.set("name", "wangjun1");
		redis.set("id", "123456");
		redis.set("address", "guangzhou");

		// SETEX key seconds value将值value关联到key，并将key的生存时间设为seconds(以秒为单位)。
		redis.setex("foo", 5, "haha");

		// MSET key value [key value ...]同时设置一个或多个key-value对。
		redis.mset("haha", "111", "xixi", "222");

		// redis.flushAll();清空所有的key
		System.out.println(redis.dbSize());// dbSize是多少个key的个数

		// APPEND key value如果key已经存在并且是一个字符串，APPEND命令将value追加到key原来的值之后。
		redis.append("foo", "00");// 如果key已经存在并且是一个字符串，APPEND命令将value追加到key原来的值之后。

		// GET key 返回key所关联的字符串值
		redis.get("foo");

		// MGET key [key ...] 返回所有(一个或多个)给定key的值
		List list = redis.mget("haha", "xixi");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	public static void main(String[] args) {
		JedisDemo t1 = new JedisDemo();
		t1.test();
	}

}