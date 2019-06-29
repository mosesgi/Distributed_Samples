package com.moses.distributed.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

public class LuaDemo {
	public static void main(String[] args) throws Exception {
		Jedis jedis = RedisManager.getJedis();
		
		String lua = "local num=redis.call('incr', KEYS[1])\n" + 
				"if tonumber(num)==1 then\n" + 
				"	redis.call('expire',KEYS[1],ARGV[1])\n" + 
				"	return 1\n" + 
				"elseif tonumber(num)>tonumber(ARGV[2]) then\n" + 
				"	return 0\n" + 
				"else\n" + 
				"	return 1\n" + 
				"end";
		
		String shaLoad = jedis.scriptLoad(lua);	//让Redis缓存
		List<String> keys = new ArrayList<>();
		keys.add("ip:limit:127.0.0.1");
		List<String> argus = new ArrayList<>();
		argus.add("6000");
		argus.add("10");
		Object obj = jedis.evalsha(shaLoad, keys, argus);		//传摘要运行脚本
		System.out.println(obj);
	}
}
