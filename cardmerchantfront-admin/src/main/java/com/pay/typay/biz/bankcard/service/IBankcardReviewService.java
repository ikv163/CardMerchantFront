package com.pay.typay.biz.bankcard.service;

import com.pay.typay.biz.bankcard.domain.BankcardReview;
import java.util.List;

/**
 * 代理银行卡审核Service接口
 * 
 * @author ruoyi
 * @date 2020-07-19
 */
public interface IBankcardReviewService 
{
    /**
     * 查询代理银行卡审核
     * 
     * @param id 代理银行卡审核ID
     * @return 代理银行卡审核
     */
     BankcardReview selectBankcardReviewById(Long id);

    /**
     * 查询代理银行卡审核列表
     * 
     * @param tAgentBankcardReview 代理银行卡审核
     * @return 代理银行卡审核集合
     */
     List<BankcardReview> selectBankcardReviewList(BankcardReview tAgentBankcardReview);

    /**
     * 新增代理银行卡审核
     * 
     * @param tAgentBankcardReview 代理银行卡审核
     * @return 结果
     */
     int insertBankcardReview(BankcardReview tAgentBankcardReview);

    /**
     * 修改代理银行卡审核
     * 
     * @param tAgentBankcardReview 代理银行卡审核
     * @return 结果
     */
     int updateBankcardReview(BankcardReview tAgentBankcardReview);

    /**
     * 批量删除代理银行卡审核
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteBankcardReviewByIds(String ids);

    /**
     * 删除代理银行卡审核信息
     * 
     * @param id 代理银行卡审核ID
     * @return 结果
     */
     int deleteBankcardReviewById(Long id);

    /**
     * 银行卡是否有未完成该订单
     * @param bankid
     * @return
     */
    BankcardReview selectBankcardReviewByBankId(Long bankid);
}
