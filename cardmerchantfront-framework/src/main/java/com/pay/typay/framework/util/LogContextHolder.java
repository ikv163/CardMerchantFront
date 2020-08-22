package com.pay.typay.framework.util;

import com.pay.typay.common.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LogContextHolder {

    private static final ThreadLocal contextHolder = new ThreadLocal<>();
    /**
     * 设置数据源
     */
    public static void setLogType( List<JSONObject> list) {
        contextHolder.set(list);
    }

    /**
     * 取得当前数据源
     */
    public static List<JSONObject> getLogType() {
        if(contextHolder.get()==null){
            return new ArrayList<>();
        }
        return (List<JSONObject>) contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clearLogType() {
        contextHolder.remove();
    }
}
