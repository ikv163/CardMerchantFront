package com.pay.typay.system.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.annotation.Excel.ColumnType;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 角色表 sys_role
 *
 * @author js-oswald
 */
@Data
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 授权用户id
     */
    private Long opuserid;
    private Long userId;
    /**
     * 复制用户权限id
     */
    private Long copyuserid;
    /**
     * 数据权限id
     */
    private Long datascopeId;
    /**
     * 银行卡id数组
     */
    private String bankcardIds;
    private String bankcardpool;
    /**
     * 支付渠道id数组
     */
    private String paychannelIds;

    /**
     * 渠道权限
     */
    private Long channelScope;
    /**
     * 渠道权限
     */
    private Long cardpoolScope;
    /**
     * 角色ID
     */
    @Excel(name = "角色序号", cellType = ColumnType.NUMERIC)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long roleId;

//    private Long userId;



    /**
     * 角色名称
     */
    @Excel(name = "角色名称")
    private String roleName;
    private String parentroleName;

    /**
     * 角色权限
     */
    @Excel(name = "角色权限")
    private String roleKey;

    /**
     * 角色排序
     */
    @Excel(name = "角色排序", cellType = ColumnType.NUMERIC)
    private String roleSort;

    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限）
     */
    @Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限")
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）
     */
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    private boolean flag = false;

    /**
     * 菜单组
     */
    private Long[] menuIds;


    public SysRole() {

    }

    public SysRole(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }


    public boolean isAdmin()
    {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId)
    {
        return roleId != null && 1L == roleId;
    }



    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @NotBlank(message = "权限字符不能为空")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    @NotBlank(message = "显示顺序不能为空")
    public String getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(String roleSort) {
        this.roleSort = roleSort;
    }

    public String getStatus() {
        return status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Long[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Long[] menuIds) {
        this.menuIds = menuIds;
    }


}
