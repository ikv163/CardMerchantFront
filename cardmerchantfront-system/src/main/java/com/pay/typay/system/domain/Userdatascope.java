package com.pay.typay.system.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * userdatascope对象 sys_user_datascope
 *
 * @author oswald
 * @date 2020-01-23
 */
@Data
public class Userdatascope extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** null */
    @Excel(name = "null")
    private Long userId;

    /** null */
    @Excel(name = "null")
    private Long datascopeId;


}