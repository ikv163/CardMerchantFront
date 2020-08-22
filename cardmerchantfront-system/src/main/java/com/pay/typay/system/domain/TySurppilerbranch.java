package com.pay.typay.system.domain;

import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;


/**
 * 供应商公司信息对象 t_surppilerbranch
 * 
 * @author warren
 * @date 2020-08-7
 */
public class TySurppilerbranch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long companyid;

    /** 公司名称 */
    @Excel(name = "公司名称")
    private String branchname;

    /** 状态,1:启用，0：停用，-1：删除 */
    @Excel(name = "状态,1:启用，0：停用，-1：删除")
    private Long status;

    private Long createby;

    private Long updateby;


    private Date updatetime;

    /** 父级财务id */
    @Excel(name = "父级财务id")
    private Long parentId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCompanyid(Long companyid) 
    {
        this.companyid = companyid;
    }

    public Long getCompanyid() 
    {
        return companyid;
    }
    public void setBranchname(String branchname) 
    {
        this.branchname = branchname;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getCreateby() {
        return createby;
    }

    public void setCreateby(Long createby) {
        this.createby = createby;
    }

    public Long getUpdateby() {
        return updateby;
    }

    public void setUpdateby(Long updateby) {
        this.updateby = updateby;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getBranchname()
    {
        return branchname;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyid", getCompanyid())
            .append("branchname", getBranchname())
            .append("status", getStatus())
            .append("parentId", getParentId())
            .toString();
    }
}
