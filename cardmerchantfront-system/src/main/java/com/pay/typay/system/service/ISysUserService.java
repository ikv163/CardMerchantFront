package com.pay.typay.system.service;

import com.pay.typay.system.domain.SysUser;

import java.util.List;

/**
 * 用户 业务层
 * 
 * @author js-oswald
 */
public interface ISysUserService
{


    public List<SysUser> selectUserList(SysUser user);

    public List<SysUser> selectUserListFront(SysUser user);

    /**
     * 超管账号查询
     * @param user
     * @return
     */
    public List<SysUser> selectAdminList(SysUser user);
    /**
     * 根据条件分页查询已分配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

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
    SysUser selectAdminUserByLoginName(String userName);

    /**
     * 通过用户名查询秘钥
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    String getGoogleCodeByLoginName(String userName);
    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteUserByIds(String ids) throws Exception;

    /**
     * 保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 修改用户详细信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserInfo(SysUser user);

    /**
     * 修改用户密码信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int resetUserPwd(SysUser user);

    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 登录名称
     * @return 结果
     */
    public String checkLoginNameUnique(String loginName);
    public boolean checkLoginNameValided(String loginName);



    /**
     * 校验用户是否允许操作
     * 
     * @param user 用户信息
     */
    public void checkUserAllowed(SysUser user);

    /**
     * 根据用户ID查询用户所属角色组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public String selectUserRoleGroup(Long userId);


    /**
     * 导入用户数据
     * 
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

    /**
     * 用户状态修改
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int changeStatus(SysUser user);


    int updateUserBranch(SysUser user);

    int updateUserGoogleCode(SysUser user);


    /**
     * 统计当前财务分支下的用户数量
     * @param supplierBranchId 财务分支ID
     * @return 结果
     */
    public int countUserByBranchId(long supplierBranchId);
    /**
     * 删除当前财务分支下的所有用户、用户角色关联、银行卡角色关联信息
     * @param supplierBranchId 财务分支ID
     * @return 结果
     */
    public int deleteRoleRelByBranchId(long supplierBranchId);
    /**
     * 通过财务分支ID查询用户列表
     * @param supplierBranchId 财务分支ID
     * @return 结果
     */
    public List<SysUser> queryUserListBy(Long supplierBranchId);
}
