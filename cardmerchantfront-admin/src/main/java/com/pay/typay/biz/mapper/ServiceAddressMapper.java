package com.pay.typay.biz.mapper;

import com.pay.typay.biz.domain.TServiceAddress;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 服务地址配置表 Mapper 接口
 * </p>
 *   @author Warren
 *   @date 2020-01-05
 */
public interface ServiceAddressMapper {

    /**
     * 获取数据库服务配置值
     * @param serviceKey
     * @return
     */
    public TServiceAddress getConfig(@Param("serviceKey") String serviceKey);

    /**
     *
     * @return
     */
    public int update(@Param("serviceAddress") String serviceAddress, @Param("serviceKey") String serviceKey);

    int insertInto(TServiceAddress tServiceAddress);
}
