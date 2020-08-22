package com.pay.typay.biz.service.impl;

import com.pay.typay.biz.domain.TServiceAddress;
import com.pay.typay.biz.mapper.ServiceAddressMapper;
import com.pay.typay.biz.service.IServiceConfigService;
import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.cache.RedisUtil;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 数据库服务配置Service业务层处理
 *
 * @author Warren
 * @date 2020-01-08
 */
@Slf4j
@Service
public class ServiceConfigServiceImpl implements IServiceConfigService {
    @Autowired
    private ServiceAddressMapper serviceAddressMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取数据库服务配置值
     * @param serviceKey
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.tyadmin)
    public String getConfig(String serviceKey) {
        //优先从缓存读取
        log.info("serviceKey={}",serviceKey);
    	Object jsonString = redisUtil.hget("general:service:"+serviceKey, serviceKey);
    	 log.info("jsonString={}",jsonString);
    	if(null != jsonString) {
    		TServiceAddress serviceAddress = JsonUtils.jsonToPojo(jsonString.toString(),TServiceAddress.class);
    		return serviceAddress.getServiceAddress();
    	}else {
            TServiceAddress serviceAddress = serviceAddressMapper.getConfig(serviceKey);
    		 log.info("serviceAddress{}",serviceAddress);
    		 return Objects.isNull(serviceAddress) ? "" : serviceAddress.getServiceAddress();
    	}
    }
}
