package com.pay.typay.generator.service.impl;

import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.generator.domain.GenTableColumn;
import com.pay.typay.generator.mapper.GenTableColumnMapper;
import com.pay.typay.generator.service.IGenTableColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务字段 服务层实现
 *
 * @author js-oswald
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService {
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * 查询业务字段列表
     *
     * @param genTableColumn 业务字段信息
     * @return 业务字段集合
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    public List<GenTableColumn> selectGenTableColumnListByTableId(GenTableColumn genTableColumn) {
        return genTableColumnMapper.selectGenTableColumnListByTableId(genTableColumn);
    }

    /**
     * 新增业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    public int insertGenTableColumn(GenTableColumn genTableColumn) {
        return genTableColumnMapper.insertGenTableColumn(genTableColumn);
    }

    /**
     * 修改业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    public int updateGenTableColumn(GenTableColumn genTableColumn) {
        return genTableColumnMapper.updateGenTableColumn(genTableColumn);
    }

    /**
     * 删除业务字段对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @DataSource(value = DataSourceType.typayv2)
    public int deleteGenTableColumnByIds(String ids) {
        return genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
    }
}