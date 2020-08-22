package com.pay.typay.system.mapper;

import com.pay.typay.system.domain.TySurppilerbranch;

import java.util.List;

/**
 * 供应商公司信息Mapper接口
 *
 * @author warren
 * @date 2020-08-7
 */
public interface TySurppilerbranchMapper {
    /**
     * 查询供应商公司信息的财务分支及子级财务分支
     *
     * @param id 供应商公司信息ID
     * @return 供应商公司信息
     */
    List<TySurppilerbranch> selectTSurppilerbranchByParentId(Long id);
}
