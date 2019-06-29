package com.moses.distributed.redis;

import java.util.List;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RedisLock {
	
	public String getLock(String key, int timeout) {
		try {
			Jedis jedis = RedisManager.getJedis();
			String value = UUID.randomUUID().toString();
			long end = System.currentTimeMillis() + timeout;
			
			while(System.currentTimeMillis()<end) {		//阻塞
				if(jedis.setnx(key, value) == 1) {
					//lock success, redis success
					jedis.expire(key, timeout);
					return value;
				}
				if(jedis.ttl(key) == -1) {		//检测过期时间，-1 代表没设
					jedis.expire(key, timeout);
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean releaseLock(String key, String value) {
		try {
			Jedis jedis = RedisManager.getJedis();
			
			while(true) {
				jedis.watch(key);		//watch, 一旦key被更改或删除，后面的事务代码就不会执行
				if(value.equals(jedis.get(key))) {		//判断获得锁的线程和当前 Redis中存在的锁是同一个
					Transaction transaction = jedis.multi();
					transaction.del(key);
					List<Object> list = transaction.exec();
					if(list== null) {
						continue;
					}
					return true;
				}
				jedis.unwatch();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		RedisLock lock = new RedisLock();
		String lockId = lock.getLock("lock:aaa", 10000);
		if(lockId != null) {
			System.out.println("获得锁成功！");
		} else {
			System.out.println("获得锁失败.");
		}
		
		String lockAgain = lock.getLock("lock:aaa", 10000);
		System.out.println(lockAgain);
	}
}
