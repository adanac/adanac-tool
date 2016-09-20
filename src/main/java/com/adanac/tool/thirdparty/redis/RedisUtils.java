package com.adanac.tool.thirdparty.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adanac.framework.cache.redis.client.ShardedJedisPipelineAction;
import com.adanac.framework.cache.redis.client.impl.MyShardedClient;

import redis.clients.jedis.ShardedJedisPipeline;

/**
 * redis工具
 */
public class RedisUtils {

	private final static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

	/**
	 * 功能描述: <br>删除collectKey里面的所有key相关数据集
	 * 〈功能详细描述〉
	 *
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static void clearRedisByKey(MyShardedClient jedis, String collectKey) {
		long start = System.currentTimeMillis();

		Long len = jedis.llen(collectKey);
		logger.info("delAll---all keys len=" + len);
		if (len == 0) {
			return;
		}
		int page = 100000;

		int totalPage = 0;
		if (len % page == 0) {
			totalPage = (int) (len / page);
		} else {
			totalPage = (int) (len / page) + 1;
		}
		logger.info("delAll---totalPage=" + totalPage);

		for (int i = 1; i <= totalPage; i++) {

			int startS = (i - 1) * page;
			final int endS = i * page;

			if (i == 1) {
				startS = 0;
			} else {
				startS = startS + 1;
			}

			List<String> keys = jedis.lrange(collectKey, startS, endS);
			logger.info("delAll-success-startS=[" + startS + "],endS=[" + endS + "],keys size=" + keys.size());
			if (keys == null || keys.isEmpty()) {
				logger.info("del keys null");
				continue;
			}
			// 通过管道进行删除
			// 通过管道往redist中进行存放
			jedis.execute(keys, new ShardedJedisPipelineAction<Object>() {
				public List<Object> doAction(ShardedJedisPipeline pipeline, Object inParam) {
					List<String> nlist = (ArrayList<String>) inParam;
					for (String s : nlist) {
						pipeline.del(s);
					}
					return pipeline.syncAndReturnAll();
				}
			});
		}

		try {
			jedis.del(collectKey);// 可能会超时
		} catch (Exception e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		logger.info("delAll key cost time=" + (end - start));
	}

}
