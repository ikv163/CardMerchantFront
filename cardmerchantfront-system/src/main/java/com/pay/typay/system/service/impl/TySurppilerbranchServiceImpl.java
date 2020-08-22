package com.pay.typay.system.service.impl;


import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.system.domain.TySurppilerbranch;
import com.pay.typay.system.mapper.TySurppilerbranchMapper;
import com.pay.typay.system.service.TySurppilerbranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


/**
 * 供应商公司信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-06-22
 */
@Service
@DataSource(DataSourceType.typayv2)
public class TySurppilerbranchServiceImpl implements TySurppilerbranchService
{
    @Autowired
    private TySurppilerbranchMapper tysurppilerbranchMapper;

    /**
     *  获取供应商的财务分支ID及子级财务分支ID
     * @param id 供应商公司信息ID
     * @return
     */
    @Override
    public String selectTSurppilerbranchByParentId(Long id) {
        List<TySurppilerbranch> tSurppilerbranches = tysurppilerbranchMapper.selectTSurppilerbranchByParentId(id);
        List<Long> supplierbranchids = tSurppilerbranches.stream().map(TySurppilerbranch::getId).collect(Collectors.toList());
        supplierbranchids.add(id);
        AtomicReference<String> beforeStrIds = new AtomicReference<>("");
        beforeStrIds.set(supplierbranchids.stream().map(aLong -> aLong.toString()).collect(Collectors.joining(",")));
        return beforeStrIds.toString();

    }

}
