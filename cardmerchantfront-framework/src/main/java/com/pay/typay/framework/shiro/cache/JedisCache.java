package com.pay.typay.framework.shiro.cache;

import com.pay.typay.common.constant.Constants;
import com.pay.typay.common.jedis.JedisConfig;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.cache.Cache;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;
import java.util.*;

/**
 * @ClassName JedisCache
 * @Description
 * @Author JS-oswald
 * @Date 2020/2/21 下午5:52
 **/
public class JedisCache<K, V> implements Cache<K, V>, Serializable {
    private static final String PREFIX = "SHIRO_SESSION_ID";
    private JedisCluster jedisCluster = JedisConfig.getjedisCluster();

    private byte[] getByteKey(K k) {
        if (k instanceof String) {
            String key = PREFIX + k;
            return key.getBytes();
        } else {
            return SerializationUtils.serialize((Serializable) k);
        }
    }

    @Override
    public int size() {
//        Jedis jedis = JedisConfig.getJedis();
//        Long size = jedis.dbSize();
//        JedisConfig.closeJedis(jedis);
//        return size.intValue();
        return 0;
    }

    @Override
    public Set<K> keys() {
//        Jedis jedis = JedisConfig.getJedis();
//
//        Set<byte[]> bytes = jedis.keys((PREFIX + new String("*")).getBytes());
//        Set<K> keys = new HashSet<>();
//        if (bytes != null) {
//            for (byte[] b : bytes) {
//                keys.add(SerializationUtils.deserialize(b));
//            }
//        }
//        JedisConfig.closeJedis(jedis);
//        return keys;
        Set<byte[]> bytes = jedisCluster.hkeys((PREFIX + new String("*")).getBytes());
        Set<K> keys = new HashSet<>();
        if (bytes != null) {
            for (byte[] b : bytes) {
                keys.add(SerializationUtils.deserialize(b));
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
//        Set<K> keys = this.keys();
//        Jedis jedis = JedisConfig.getJedis();
//        List<V> lists = new ArrayList<>();
//        for (K k : keys) {
//            byte[] bytes = jedis.get(getByteKey(k));
//            lists.add(SerializationUtils.deserialize(bytes));
//        }
//        JedisConfig.closeJedis(jedis);
//        return lists;

        Set<K> keys = this.keys();
        List<V> lists = new ArrayList<>();
        for (K k : keys) {
            byte[] bytes = jedisCluster.get(getByteKey(k));
            lists.add(SerializationUtils.deserialize(bytes));
        }
        return lists;
    }

    @Override
    public void clear() {
//        JedisConfig.getJedis().flushDB();
//        jedisCluster.flushDB()
//        JedisConfig.getJedis().flushDB();
    }

    @Override
    public V put(K k, V v) {
        jedisCluster.set(getByteKey(k), SerializationUtils.serialize((Serializable) v));
        jedisCluster.expire(getByteKey(k), Constants.EXPRIE);
        byte[] bytes = jedisCluster.get(SerializationUtils.serialize(getByteKey(k)));
        if (bytes == null) {
            return null;
        }
        return SerializationUtils.deserialize(bytes);
//        Jedis jedis = JedisConfig.getJedis();
//        jedis.set(getByteKey(k), SerializationUtils.serialize((Serializable) v));
//        jedis.expire(getByteKey(k), Constants.EXPRIE);
//        byte[] bytes = jedis.get(SerializationUtils.serialize(getByteKey(k)));
//        JedisConfig.closeJedis(jedis);
//        if (bytes == null) {
//            return null;
//        }
//        return SerializationUtils.deserialize(bytes);
    }

    @Override
    public V get(K k) {
        if (k == null) {
            return null;
        }
        byte[] bytes = jedisCluster.get(getByteKey(k));
        if (bytes == null) {
            return null;
        }
        return SerializationUtils.deserialize(bytes);
//        if (k == null) {
//            return null;
//        }
//        //System.out.println(k);
//        Jedis jedis = JedisConfig.getJedis();
//        byte[] bytes = jedis.get(getByteKey(k));
//        JedisConfig.closeJedis(jedis);
//        if (bytes == null) {
//            return null;
//        }
//        return SerializationUtils.deserialize(bytes);
    }

    @Override
    public V remove(K k) {
        byte[] bytes = jedisCluster.get(getByteKey(k));
        jedisCluster.del(getByteKey(k));
        if (bytes == null) {
            return null;
        }
        return SerializationUtils.deserialize(bytes);
//        Jedis jedis = JedisConfig.getJedis();
//        byte[] bytes = jedis.get(getByteKey(k));
//        jedis.del(getByteKey(k));
//        JedisConfig.closeJedis(jedis);
//        if (bytes == null) {
//            return null;
//        }
//        return SerializationUtils.deserialize(bytes);
    }
}

