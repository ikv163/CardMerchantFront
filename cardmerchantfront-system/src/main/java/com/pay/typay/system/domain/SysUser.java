package com.pay.typay.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pay.typay.common.annotation.ChineseName;
import com.pay.typay.common.annotation.Excel;
import com.pay.typay.common.annotation.Excel.ColumnType;
import com.pay.typay.common.annotation.Excel.Type;
import com.pay.typay.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author js-oswald
 */
@Data
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long supplierbranchid;
    private String supplierbranchidgroup;
    @ChineseName("订单角色")
    private Long orderallotrule;

    /**
     * 用户ID
     */
    @Excel(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
    @ChineseName("用户ID")
    private Long userId;

    /**
     * 父ID
     */
    private Long parentId;
    private Long roleparentId;

    /**
     * 角色组
     */
    private Long[] roleIds;
    @ChineseName("角色名称")
    private String roleName;
    private Long roleId;

    /**
     * 登录名称
     */
    @Excel(name = "登录名称")
    @NotBlank(message = "登录账号不能为空")
    @Size(min = 0, max = 30, message = "登录账号长度不能超过30个字符")
    @ChineseName("登录名称")
    private String loginName;

    /**
     * 用户名称
     */
    @Excel(name = "用户名称")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    @ChineseName("用户名称")
    private String userName;
    @ChineseName("用户类型")
    private String userType;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * SN密钥
     */
    private String googlecode;
    /**
     * verifycode
     */
    private String verifycode;
    private String verifedpassword;
    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 锁定状态 0正常 1锁定*/
    @Excel(name = "登录状态", readConverterExp = "0=未锁定,1=已锁定")
    private Integer lockStatus;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登陆IP
     */
    @Excel(name = "最后登陆IP", type = Type.EXPORT)
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;

    private List<SysRole> roles;

    /**
     * 代理编码
     */
    private String agentId;

    public SysUser() {

    }

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin()
    {
        return (this.roleId !=null && 1L==this.roleId) ;
    }

    /**
     * U盾组和后台管理员
     * @return
     */
    public boolean isUroleOrAdmin()
    {
        return (this.roleId !=null && 1L==this.roleId) ||("U盾组".equals(this.getRoleName())) ;
    }


    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    private List<String> supplierBranchIdGroupList;



}
