package com.pay.typay.system.service;


/**
 * 供应商公司信息Service接口
 * 
 * @author warren
 * @date 2020-08-7
 */
public interface TySurppilerbranchService
{

    /**
     * 查询供应商公司信息的财务分子及子级财务分支
     *
     * @param id 供应商公司信息ID
     * @return 供应商公司信息
     */
    String selectTSurppilerbranchByParentId(Long id);
}
