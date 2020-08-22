package com.pay.typay.common.cache.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.typay.common.cache.RedisUtil;
import com.pay.typay.common.constant.RedisConstant;
import com.pay.typay.common.utils.DateUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * redis操作实现类
 */
@Service
public class RedisUtilImpl implements RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Override
    public <K, V> void set(K k, V v) {
        redisTemplate.opsForValue().set(k,v);
    }

    @Override
    public <K> Object get(K k) {
        Object o = redisTemplate.opsForValue().get(k);
        return o;
    }

    @Override
    public <K> void del(K k) {
        redisTemplate.delete(k);
    }

    @Override
    public int size() {
        return 0;
    }
    @Override
    public <K> Set<K> keys(K pattern) {
        Set keys = redisTemplate.keys(pattern);
        return keys;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    @Override
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递增
     *
     * @param key 键
     * @return
     */
    @Override
    public long incr(String key) {
        if (StringUtils.isEmpty(key)) {
            key = RedisConstant.DEFAULTLIST;
        }
        return redisTemplate.opsForValue().increment(key, 1);
    }


    /**
     * 递减操作
     *
     * @param key
     * @param by
     * @return
     */
    @Override
    public Long decr(String key, Long by) {
        return redisTemplate.opsForValue().increment(key, -by);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    @Override
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean set(String key, String value) {
        boolean result = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
        return result;
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     * 217
     */
    @Override
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    @Override
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    @Override
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    @Override
    public Long hdel(String key, String item) {
        return redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key 键
     * @param map 值
     * @return true 成功 false失败
     * 217
     */
    @Override
    public boolean hsetMap(String key, Map map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key      键
     * @param map      值
     * @param time     时间 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @param timeUnit 时间单位
     * @return true 成功 false失败
     * 217
     */
    @Override
    public boolean hsetMap(String key, Map map, int time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            redisTemplate.expire(key, time, timeUnit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    @Override
    public Object get(String key) {
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            Object object = redisTemplate.opsForValue().get(key);
            Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
            redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
            return object;
        }
    }


    @Override
    public Object getWithOutDefaultSerializer(String key) {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        Object obj = redisTemplate.opsForValue().get(key);
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    @Override
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    @Override
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存<br>
     * 根据key精确匹配删除
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    @Override
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 获取某一微秒时间的自增序列（用于订单号生成）
     *
     * @param seqKey key名称
     * @return 自增值
     */
    @Override
    public long getCurrentIncrementSeq(String seqKey) {
        RedisAtomicLong counter = new RedisAtomicLong(seqKey, redisTemplate.getConnectionFactory());
        //返回redis计数
        return counter.incrementAndGet();
    }

    /**
     * 获取自增序列
     *
     * @param seqKey key名称
     * @return 自增值
     */
    @Override
    public long getIncrementSeq(String seqKey) {
        RedisAtomicLong counter = new RedisAtomicLong(seqKey, redisTemplate.getConnectionFactory());
        //唯一id：当前时间字符串精确到微秒第四位+redis计数
        String nowStr = DateUtils.parseDateToStr(DateUtils.DATE_TIME_FORMAT_MMDDHHMISS, new Date()) + counter.incrementAndGet();
        if (nowStr.length() > 19) {
            nowStr = nowStr.substring(0, 19);
        }
        return Long.parseLong(nowStr);
    }

    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public RLock lock(String lockKey, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(String lockKey, int waitTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean tryLockWithLeaseTime(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(0, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean tryLockWithLeaseTime(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(0, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock rLock = redissonClient.getLock(lockKey);
        try {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        } catch (Exception e) {
            //
        }
    }

    @Override
    public void unlock(RLock rLock) {
        try {
            if (null != rLock && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        } catch (Exception e) {
            //
        }
    }

    @Override
    public Long tableIdGenerate(String tableName, Integer type) {
        String reqKey = RedisConstant.LOCK_TABLE_ID_GENERATE.concat(tableName);
        return getIncrementSeq(reqKey);
    }


}