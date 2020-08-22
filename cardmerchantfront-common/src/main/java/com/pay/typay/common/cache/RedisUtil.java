package com.pay.typay.common.cache;


import org.redisson.api.RLock;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作类接口
 */
public interface RedisUtil {
    int size();

    <K> Set<K> keys(K pattern);

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    long incr(String key, long delta);

    /**
     * 递增
     *
     * @param key 键
     * @return
     */
    long incr(String key);


    /**
     * 递减操作
     *
     * @param key
     * @param by
     * @return
     */

    Long decr(String key, Long by);

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    boolean set(String key, Object value);


    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    boolean set(String key, Object value, long time);


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     * 217
     */
    boolean hset(String key, String item, Object value);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time);

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    Object hget(String key, String item);

    /**
     * hdel
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Long hdel(String key, String item);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key 键
     * @param map 值
     * @return true 成功 false失败
     * 217
     */
    boolean hsetMap(String key, Map map);

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
    boolean hsetMap(String key, Map map, int time, TimeUnit timeUnit);

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);


    boolean set(String key, String value);



    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    Map<Object, Object> hmget(String key);

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    boolean expire(String key, long time);

    /**
     * 删除缓存<br>
     * 根据key精确匹配删除
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    void del(String... key);

    /**
     * 获取某一微秒时间的自增序列（用于订单号生成）
     *
     * @param seqKey key名称
     * @return 自增值
     */
     long getCurrentIncrementSeq(String seqKey);

    /**
     * 获取自增序列
     *
     * @param seqKey key名称
     * @return 自增值
     */
    long getIncrementSeq(String seqKey);

    Object getWithOutDefaultSerializer(String key);

    /**
     * 锁住，有锁则进入等待，不设置超时时间，一直锁住，直到调用释放方法
     *
     * @param lockKey
     * @return
     */
    RLock lock(String lockKey);

    /**
     * 锁住，有锁则进入等待，设置超时时间,单位：秒
     *
     * @param lockKey
     * @param timeout 超时自动释放
     * @return
     */
    RLock lock(String lockKey, int timeout);


    /**
     * 尝试获取锁，获取不到直接返回，获取到锁后必须手动释放锁
     *
     * @param lockKey
     * @return 返回true，表示获得锁
     */
    boolean tryLock(String lockKey);

    /**
     * 尝试获取锁，获取不到直接返回，获取锁后，带默认自动释放时间：10秒
     *
     * @param lockKey
     * @return 返回true，表示获得锁
     */
    boolean tryLockWithLeaseTime(String lockKey);

    /**
     * 尝试获取锁，可延迟等待固定等待时间，获取到锁后必须手动释放锁
     *
     * @param lockKey
     * @param waitTime 等待时间，单位为秒
     * @return 返回true，表示获得锁
     */
    boolean tryLock(String lockKey, int waitTime);


    /**
     * 尝试获取锁，获取不到直接返回，获取锁后，可根据设定时间自动释放
     *
     * @param lockKey
     * @param leaseTime 获取到锁后，自动释放时间
     * @return 返回true，表示获得锁
     */
    boolean tryLockWithLeaseTime(String lockKey, int leaseTime);

    /**
     * 尝试获取锁，可延迟等待固定等待时间，获取锁后，可根据设定时间自动释放
     *
     * @param lockKey
     * @param waitTime  等待时间，单位为秒
     * @param leaseTime 获取到锁后，自动释放时间
     * @return 返回true，表示获得锁
     */
    boolean tryLock(String lockKey, int waitTime, int leaseTime);

    /**
     * 通过lockKey解锁
     *
     * @param lockKey
     */
    void unlock(String lockKey);

    /**
     * 通过rLock解锁
     *
     * @param rLock 锁对象
     */
    void unlock(RLock rLock);

    /**
     * 获取主键id
     * @param tableName
     * @param type
     * @return
     */
    Long tableIdGenerate(String tableName, Integer type);

    <K,V> void  set(K k, V v);

    <K> Object  get(K k);

    <K> void del(K k);
}