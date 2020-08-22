package com.pay.typay.common.utils;

/**
 * @desc:
 * @author： Warren
 * @createtime： 2020/1/29
 * @modify by： Warren
 * @modify time：
 * @desc of modify：
 * @throws:
 */
public class OrderIndexUtil {

    public static String getOrderIndex(int dataCenterId, int workerId,String prefix){
        SnowflakeIdFactory factory = new SnowflakeIdFactory(dataCenterId,workerId);
        return prefix + factory.nextId();

    }
}
