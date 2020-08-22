package com.pay.typay.biz.reports.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class TempEntity {
    private List<BigDecimal> depositList;
    private List<BigDecimal> withdrawList;
    private List<Integer> transCounts;
    private List<Integer> totalCounts;
    private Map<BigDecimal,BigDecimal> map;
}
