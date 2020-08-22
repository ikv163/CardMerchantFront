package com.pay.typay.biz.bankcard.service.impl;

import java.util.Arrays;
import java.util.List;

import com.pay.typay.biz.bankcard.domain.BankcardReview;
import com.pay.typay.biz.bankcard.mapper.BankcardReviewMapper;
import com.pay.typay.biz.bankcard.service.IBankcardReviewService;
import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.utils.UtilsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
/**
 * 代理银行卡审核Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-07-19
 */
@Service
@DataSource(DataSourceType.typayv2)
public class BankcardReviewServiceImpl implements IBankcardReviewService
{
    @Autowired
    private BankcardReviewMapper tAgentBankcardReviewMapper;

    /**
     * 查询代理银行卡审核
     * 
     * @param id 代理银行卡审核ID
     * @return 代理银行卡审核
     */
    @Override
    public BankcardReview selectBankcardReviewById(Long id)
    {
        String userSupplierbranchid;
        SysUser sysUser = ShiroUtils.getSysUser();
        //如果是管理员账号
        if (sysUser.isAdmin()) {
            userSupplierbranchid = UtilsUser.getSupplierBranchIdGroup();
        } else {
            userSupplierbranchid = sysUser.getSupplierbranchid().toString();
        }
        return tAgentBankcardReviewMapper.selectBankcardReviewById(id, userSupplierbranchid);
    }

    /**
     * 查询代理银行卡审核列表
     * 
     * @param tAgentBankcardReview 代理银行卡审核
     * @return 代理银行卡审核
     */
    @Override
    public List<BankcardReview> selectBankcardReviewList(BankcardReview tAgentBankcardReview)
    {
        return tAgentBankcardReviewMapper.selectBankcardReviewList(tAgentBankcardReview);
    }

    /**
     * 新增代理银行卡审核
     * 
     * @param tAgentBankcardReview 代理银行卡审核
     * @return 结果
     */
    @Override
    public int insertBankcardReview(BankcardReview tAgentBankcardReview)
    {
        tAgentBankcardReview.setCreateTime(DateUtils.getNowDate());
        Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
        tAgentBankcardReview.setSupplierbranchid(userSupplierbranchid);
        return tAgentBankcardReviewMapper.insertBankcardReview(tAgentBankcardReview);
    }

    /**
     * 修改代理银行卡审核
     * 
     * @param tAgentBankcardReview 代理银行卡审核
     * @return 结果
     */
    @Override
    public int updateBankcardReview(BankcardReview tAgentBankcardReview)
    {
        return tAgentBankcardReviewMapper.updateBankcardReview(tAgentBankcardReview);
    }

    /**
     * 删除代理银行卡审核对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBankcardReviewByIds(String ids)
    {
        return tAgentBankcardReviewMapper.deleteBankcardReviewByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除代理银行卡审核信息
     * 
     * @param id 代理银行卡审核ID
     * @return 结果
     */
    @Override
    public int deleteBankcardReviewById(Long id)
    {
        return tAgentBankcardReviewMapper.deleteBankcardReviewById(id);
    }

    @Override
    public BankcardReview selectBankcardReviewByBankId(Long bankid) {
        return tAgentBankcardReviewMapper.selectBankcardReviewByBankId(bankid);
    }
}
