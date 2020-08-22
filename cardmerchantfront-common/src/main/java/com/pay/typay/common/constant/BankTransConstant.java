package com.pay.typay.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 银行卡交易流水常量---导出使用
 * @author： Warren
 * @createtime： 2020/1/16
 * @modify by： Warren
 * @modify time：
 * @desc of modify：
 * @throws:
 */
public class BankTransConstant {

    public static final Map<Long,String> PAY_TYPE_MAP = new HashMap<>(2);

    public static final Map<Long,String> TRANS_TYPE_MAP = new HashMap<>(8);

    public static final Map<Integer,String> USER_LEVEL_MAP = new HashMap<>(16);

    static {
        PAY_TYPE_MAP.put(1L,"入款");
        PAY_TYPE_MAP.put(0L,"出款");

        TRANS_TYPE_MAP.put(0L,"创建");
        TRANS_TYPE_MAP.put(1L,"处理中");
        TRANS_TYPE_MAP.put(2L,"成功处理");
        TRANS_TYPE_MAP.put(3L,"异常");
        TRANS_TYPE_MAP.put(4L,"补单");
        TRANS_TYPE_MAP.put(-1L,"软删除");

        USER_LEVEL_MAP.put(999,"V0");
        USER_LEVEL_MAP.put(1,"V1");
        USER_LEVEL_MAP.put(2,"V2");
        USER_LEVEL_MAP.put(3,"V3");
        USER_LEVEL_MAP.put(4,"V4");
        USER_LEVEL_MAP.put(5,"V5");
        USER_LEVEL_MAP.put(6,"V6");
        USER_LEVEL_MAP.put(7,"V7");
        USER_LEVEL_MAP.put(8,"V8");
        USER_LEVEL_MAP.put(9,"V9");
        USER_LEVEL_MAP.put(10,"V10");
    }


}
