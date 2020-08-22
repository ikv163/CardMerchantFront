package com.pay.typay.biz.service;

/**
 * 服务器配置
 */
public interface IServiceConfigService {

    /**
     * 获取数据库服务配置值
     * @param ServiceKey
     * @return
     */
    public String getConfig(String ServiceKey);

}
