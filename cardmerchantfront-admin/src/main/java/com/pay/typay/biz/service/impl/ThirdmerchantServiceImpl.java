package com.pay.typay.biz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.typay.biz.mapper.ThirdmerchantMapper;
import com.pay.typay.biz.domain.Thirdmerchant;
import com.pay.typay.biz.service.IThirdmerchantService;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
/**
 * 三方商户Service业务层处理
 * 
 * @author Warren
 * @date 2020-01-26
 */
@Service
@DataSource(DataSourceType.typayv2)
public class ThirdmerchantServiceImpl implements IThirdmerchantService 
{
    @Autowired
    private ThirdmerchantMapper tThirdmerchantMapper;

    /**
     * 查询三方商户
     * 
     * @param merchantid 三方商户ID
     * @return 三方商户
     */
    @Override
    public Thirdmerchant selectThirdmerchantById(Long merchantid)
    {
        return tThirdmerchantMapper.selectThirdmerchantById(merchantid);
    }

    /**
     * 查询三方商户列表
     * 
     * @param tThirdmerchant 三方商户
     * @return 三方商户
     */
    @Override
    public List<Thirdmerchant> selectThirdmerchantList(Thirdmerchant tThirdmerchant)
    {
        return tThirdmerchantMapper.selectThirdmerchantList(tThirdmerchant);
    }

    /**
     * 新增三方商户
     * 
     * @param tThirdmerchant 三方商户
     * @return 结果
     */
    @Override
    public int insertThirdmerchant(Thirdmerchant tThirdmerchant)
    {
        return tThirdmerchantMapper.insertThirdmerchant(tThirdmerchant);
    }

    /**
     * 修改三方商户
     * 
     * @param tThirdmerchant 三方商户
     * @return 结果
     */
    @Override
    public int updateThirdmerchant(Thirdmerchant tThirdmerchant)
    {
        return tThirdmerchantMapper.updateThirdmerchant(tThirdmerchant);
    }

    /**
     * 删除三方商户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteThirdmerchantByIds(String ids)
    {
        return tThirdmerchantMapper.deleteThirdmerchantByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除三方商户信息
     * 
     * @param merchantid 三方商户ID
     * @return 结果
     */
    @Override
    public int deleteThirdmerchantById(Long merchantid)
    {
        return tThirdmerchantMapper.deleteThirdmerchantById(merchantid);
    }
}
