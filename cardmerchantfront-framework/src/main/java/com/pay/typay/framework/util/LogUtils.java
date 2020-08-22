package com.pay.typay.framework.util;

/**
 * 处理并记录日志文件
 *
 * @author js-oswald
 */
public class LogUtils {

    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "" ;
        }
        return "[" + msg.toString() + "]" ;
    }
}
