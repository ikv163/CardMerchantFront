package com.pay.typay.biz.service;

import com.pay.typay.biz.domain.Thirdmerchant;
import java.util.List;

/**
 * 三方商户Service接口
 * 
 * @author Warren
 * @date 2020-01-26
 */
public interface IThirdmerchantService 
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
     * 批量删除三方商户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteThirdmerchantByIds(String ids);

    /**
     * 删除三方商户信息
     * 
     * @param merchantid 三方商户ID
     * @return 结果
     */
     int deleteThirdmerchantById(Long merchantid);
}
