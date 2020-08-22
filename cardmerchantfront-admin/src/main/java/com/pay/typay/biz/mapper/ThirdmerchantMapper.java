package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.Thirdmerchant;

import java.util.List;

/**
 * 三方商户Mapper接口
 * 
 * @author Warren
 * @date 2020-01-26
 */
public interface ThirdmerchantMapper 
{
    /**
     * 查询三方商户
     * 
     * @param merchantid 三方商户ID
     * @return 三方商户
     */
     Thirdmerchant selectThirdmerchantById(Long merchantid);

    /**
     * 查询三方商户列表
     * 
     * @param tThirdmerchant 三方商户
     * @return 三方商户集合
     */
     List<Thirdmerchant> selectThirdmerchantList(Thirdmerchant tThirdmerchant);

    /**
     * 新增三方商户
     * 
     * @param tThirdmerchant 三方商户
     * @return 结果
     */
     int insertThirdmerchant(Thirdmerchant tThirdmerchant);

    /**
     * 修改三方商户
     * 
     * @param tThirdmerchant 三方商户
     * @return 结果
     */
     int updateThirdmerchant(Thirdmerchant tThirdmerchant);

    /**
     * 删除三方商户
     * 
     * @param merchantid 三方商户ID
     * @return 结果
     */
     int deleteThirdmerchantById(Long merchantid);

    /**
     * 批量删除三方商户
     * 
     * @param merchantids 需要删除的数据ID
     * @return 结果
     */
     int deleteThirdmerchantByIds(String[] merchantids);
}
