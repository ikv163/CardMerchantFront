package com.pay.typay.common.constant;

public enum TableIdGenerateType {
    FORM_REDIS(0, "从Redis取ID后，再插入数据库"),
    FROM_DB(1, "直接从表t_table_id_generate中取并+1，并放入Redis"),
    ONLY_DB(2, "直接从t_table_id_generate中取，与Redis无关"),

    ;

    private int code;
    private String message;

    TableIdGenerateType(int code, String msg) {
        this.message = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}