package com.pay.typay.framework.shiro.manager;

import com.pay.typay.framework.shiro.cache.JedisCache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName JedisCacheManager
 * @Description
 * @Author JS-oswald
 * @Date 2020/2/21 下午5:57
 **/
public class JedisCacheManager implements CacheManager {
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    //cache
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        Cache cache = caches.get(s);
        if (cache == null) {
            cache = new JedisCache();
            caches.put(s, cache);
        }
        return cache;
    }
}
