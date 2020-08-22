package com.pay.typay.common.constant;

/**
 * Redis常量
 */
public class RedisConstant {

    /**
     * 分
     */
    public final static int MINUTE = 60;

    /**
     * 时
     */
    public final static int HOUR = 3600;

    /**
     * 天
     */
    public final static int DAY = 86400;

    /**
     * 周
     */
    public final static int WEEK = 604800;

    /**
     * 月
     */
    public final static int MONTH = 2592000;

    public final static String DEFAULTLIST = "default_banktrans";

    /**
     * 根目录
     */
    public final static String TYPAY = "typy";

    /**
     * 锁目录
     */
    public final static String LOCK = TYPAY.concat(":lock");

    /**
     * Redis自增ID锁
     */
    public final static String LOCK_TABLE_ID_GENERATE = LOCK.concat(":generateid:table:");
    /**
     * TOKEN前缀
     */
    public final static String REDIS_PREFIX_LOGIN = "java_login_token_%s";

}
