package com.pay.typay.system.mapper;

import com.pay.typay.system.domain.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户表 数据层
 * 
 * @author js-oswald
 */
public interface SysUserMapper
{
    /**
     * 根据条件分页查询用户列表
     * 
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectUserList(SysUser sysUser);
    List<SysUser> selectUserListFront(SysUser sysUser);
    List<SysUser> generalselectUserList(SysUser sysUser);
    List<SysUser> selectDatascopeUserList(SysUser user);

    /**
     * 根据条件分页查询未已配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String userName);

    /**
     * 通过用户名查询用户专供php跳转java
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectAdminUserByLoginName(String userName);

    /**
     * 通过用户名查询秘钥
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public String getGoogleCodeByLoginName(String userName);


    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);
    public SysUser selectUserByLoginname(String loginame);

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteUserByIds(Long[] ids);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUser user);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(SysUser user);

    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 登录名称
     * @return 结果
     */
    int checkLoginNameUnique(String loginName);


    /**
     * 检查当前财务分支下是否有用户
     *
     * @param supplierBranchId 财务分支ID
     * @return 结果
     */
    int countUserByBranchId(long supplierBranchId);

    /**
     * 删除前财务分支下所有用户
     *
     * @param supplierBranchId 财务分支ID
     * @return 结果
     */
    int deleteUserBy(long supplierBranchId);
    /**
     * 删除前财务分支下所有卡和角色关联
     *
     * @param supplierBranchId 财务分支ID
     * @return 结果
     */
    int updateBankcardRoleBy(long supplierBranchId);
    /**
     * 删除前财务分支下所有用户角色关联
     *
     * @param supplierBranchId 财务分支ID
     * @return 结果
     */
    int deleteUserRoleBy(long supplierBranchId);

    /**
     * 通过财务分支ID查询用户列表
     *
     * @param supplierBranchId 财务分支ID
     * @return 用户对象信息
     */
    public  List<SysUser> queryUserListBy(@Param(value="supplierBranchId") Long supplierBranchId);
}
