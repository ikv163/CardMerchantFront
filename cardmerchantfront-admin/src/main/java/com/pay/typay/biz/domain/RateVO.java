package com.pay.typay.biz.domain;

import com.google.common.collect.Lists;
import com.pay.typay.common.utils.JsonUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @desc: 费率设置
 * @author： Warren
 * @createtime： 2020/1/23
 * @modify by： Warren
 * @modify time：
 * @desc of modify：
 * @throws:
 */
@Data
public class RateVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 1:按单笔金额,2:按百分比
     */
    private String rateFlag;

    /**
     * 渠道ID数组字符串
     */
    private String paychannelids;
    /**
     * 1、收款
     * 2、出款
     * 0、出入款
     */
    private int worktype;

    /**
     * 费率规则集合 最多3条
     */
    private List<Rule> rules;

    @Data
    static
    class Rule{
        /**
         * 金额/百分比
         */
        private String rateValue;
        /**
         * 触发条件的最低值
         */
        private String minRange;
        /**
         * 触发条件的最大值
         */
        private String maxRange;
        /**
         * 费率生效起始时间,如00:00:00
         */
        private String rateStartTime;
        /**
         * 费率生效截止时间,如12:00:00
         */
        private String rateEndTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"rateFlag\":\"").append(rateFlag).append("\",")
                .append("\"worktype\":\"").append(worktype).append("\",")
                .append("\"rules\":").append("[");
        rules.stream().forEach(rule ->
                sb.append("{\"maxRange\":\"").append(rule.getMaxRange()).append("\",")
                .append("\"rateValue\":\"").append(rule.getRateValue()).append("\",")
                .append("\"minRange\":\"").append(rule.getMinRange()).append("\",")
                .append("\"rateStartTime\":\"").append(rule.getRateStartTime()).append("\",")
                .append("\"rateEndTime\":\"").append(rule.getRateEndTime()).append("\"},")
        );
        if(!rules.isEmpty()){
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("]").append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        RateVO r = new RateVO();
        r.setRateFlag("1");
        Rule rule1 = new Rule();
        rule1.setMaxRange("10000");
        rule1.setMinRange("1");
        rule1.setRateValue("4");
        rule1.setRateStartTime("00:00:00");
        rule1.setRateEndTime("23:59:59");
        List<Rule> rules = Lists.newArrayList();
        rules.add(rule1);
        rules.add(rule1);
        r.setRules(rules);
        System.out.println(r.toString());

        System.out.println(JsonUtils.objectToJson(r));
    }
}
