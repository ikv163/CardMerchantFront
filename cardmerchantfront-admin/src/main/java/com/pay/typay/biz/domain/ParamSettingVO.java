package com.pay.typay.biz.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @desc: 参数设置
 * @author： Warren
 * @createtime： 2020/1/23
 * @modify by： Warren
 * @modify time：
 * @desc of modify：
 * @throws:
 */
@Data
public class ParamSettingVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 开关
     */
    private Boolean status;

    /**
     * 出款开关
     */
    private Boolean withdrawStatus;
    /**
     * 出款限额开关
     */
    private Boolean withdrawAmountStatus;
    /**
     * 超额
     */
    private String amount;

    /**
     * 收款笔数
     */
    private int transTimes;

    /**
     * 成功率
     */
    private String successRate;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[{\"transTimes\":\"").append(transTimes).append("\",")
                .append("\"successRate\":\"").append(successRate).append("\",")
                .append("\"withdrawStatus\":\"").append(withdrawStatus).append("\",")
                .append("\"withdrawAmountStatus\":\"").append(withdrawAmountStatus).append("\",")
                .append("\"amount\":\"").append(amount).append("\",")
                .append("\"status\":\"").append(status).append("\"}]");
        return sb.toString();
    }
}
