package com.pay.typay.biz.reports.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportUtil {
    /**
     * 计算分润
     * @param parentRatio   卡商费率
     * @param ratioMap   <代理费率,代理充值金额>
     */
    public static BigDecimal getSplitProfit(BigDecimal parentRatio , Map<BigDecimal,BigDecimal> ratioMap){
        return ratioMap.entrySet().stream().map(e -> {return e.getValue().multiply(parentRatio.subtract(e.getKey()));}).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /**
     * 计算代理总充值金额
     * @param childList
     * @return
     */
    public static TempEntity buildTempData(List<ReportsDayChildVO> childList){
        TempEntity temp = new TempEntity();
        Map<BigDecimal,BigDecimal> map = new HashMap<>();
        List<BigDecimal> depositList = new ArrayList<>();
        List<BigDecimal> withdrawList = new ArrayList<>();
        List<Integer> transCounts = new ArrayList<>();
        List<Integer> totalCounts = new ArrayList<>();
        for (ReportsDayChildVO child : childList) {
            BigDecimal decimal = getTotalDepositAmount(child);
            map.put(child.getRatio(),decimal);
            depositList.add(decimal);
            withdrawList.add(getTotalWithdrawAmount(child));
            transCounts.add(getTotalTransCount(child));
            totalCounts.add(getTotalCounts(child));
        }
        temp.setMap(map);
        temp.setDepositList(depositList);
        temp.setWithdrawList(withdrawList);
        temp.setTotalCounts(totalCounts);
        temp.setTransCounts(transCounts);
        return temp;
    }

    /**
     * 代理入款求和
     * @param dayChildVO
     * @return
     */
    public static BigDecimal getTotalDepositAmount(ReportsDayChildVO dayChildVO){
        BigDecimal result = dayChildVO.getDepositAmount();
        List<ReportsDayChildVO> childList = dayChildVO.getChildList();
        if (childList == null || childList.isEmpty()){
            return result;
        }
        for (ReportsDayChildVO reportsDayChildVO : childList) {
            if (childList.isEmpty()){
                break;
            }
            result = result.add(getTotalDepositAmount(reportsDayChildVO));
        }
        return result;
    }

    /**
     * 代理出款求和
     * @param dayChildVO
     * @return
     */
    public static BigDecimal getTotalWithdrawAmount(ReportsDayChildVO dayChildVO){
        BigDecimal result = dayChildVO.getWithdrawAmount();
        List<ReportsDayChildVO> childList = dayChildVO.getChildList();
        if (childList == null || childList.isEmpty()){
            return result;
        }
        for (ReportsDayChildVO reportsDayChildVO : childList) {
            if (childList.isEmpty()){
                break;
            }
            result = result.add(getTotalWithdrawAmount(reportsDayChildVO));
        }
        return result;
    }

    /**
     * 代理交易次数求和
     * @param dayChildVO
     * @return
     */
    public static int getTotalTransCount(ReportsDayChildVO dayChildVO){
        int result = dayChildVO.getTransCount();
        List<ReportsDayChildVO> childList = dayChildVO.getChildList();
        if (childList == null || childList.isEmpty()){
            return result;
        }
        for (ReportsDayChildVO reportsDayChildVO : childList) {
            if (childList.isEmpty()){
                break;
            }
            result += getTotalTransCount(reportsDayChildVO);
        }
        return result;
    }

    /**
     * 代理交易次数求和
     * @param dayChildVO
     * @return
     */
    public static int getTotalCounts(ReportsDayChildVO dayChildVO){
        int result = dayChildVO.getTotalCount();
        List<ReportsDayChildVO> childList = dayChildVO.getChildList();
        if (childList == null || childList.isEmpty()){
            return result;
        }
        for (ReportsDayChildVO reportsDayChildVO : childList) {
            if (childList.isEmpty()){
                break;
            }
            result += getTotalCounts(reportsDayChildVO);
        }
        return result;
    }
}
