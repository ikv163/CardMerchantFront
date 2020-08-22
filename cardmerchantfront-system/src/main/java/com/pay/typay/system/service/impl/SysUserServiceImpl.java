package com.pay.typay.system.service.impl;

import com.pay.typay.common.constant.UserConstants;
import com.pay.typay.common.core.text.Convert;
import com.pay.typay.common.exception.BusinessException;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.common.utils.security.Md5Utils;
import com.pay.typay.system.domain.SysRole;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.domain.SysUserRole;
import com.pay.typay.system.mapper.SysRoleMapper;
import com.pay.typay.system.mapper.SysUserMapper;
import com.pay.typay.system.mapper.SysUserRoleMapper;

import com.pay.typay.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户 业务层处理
 *
 * @author js-oswald
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper userMapper;

    @Resource
    private SysRoleMapper roleMapper;


    @Resource
    private SysUserRoleMapper userRoleMapper;




    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override

    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public List<SysUser> selectUserListFront(SysUser user) {
        return userMapper.selectUserListFront(user);
    }

    @Override

    public List<SysUser> selectAdminList(SysUser user) {
        user.getParams().put("dataScope", "");
        user.setRoleId(1L);
        return userMapper.generalselectUserList(user);
    }



    @Override
    public List<SysUser> queryUserListBy(Long supplierBranchId) {
        return userMapper.queryUserListBy(supplierBranchId);
    }


    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
//
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
//
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByLoginName(String userName) {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过用户名查询用户专供php跳转java
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectAdminUserByLoginName(String userName){
        return userMapper.selectAdminUserByLoginName(userName);
    }
    /**
     * 通过用户名查询秘钥
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public String getGoogleCodeByLoginName(String userName) {
        return userMapper.getGoogleCodeByLoginName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(String ids) throws BusinessException {
        Long[] userIds = Convert.toLongArray(ids);
        return userMapper.deleteUserByIds(userIds);
    }


    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        //1. 新增用户信息
        int rows = userMapper.insertUser(user);


        //3. 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);

        return userMapper.updateUser(user);
    }
    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUserBranch(SysUser user) {
        return userMapper.updateUser(user);
    }

    @Override
    @Transactional
    public int updateUserGoogleCode(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(SysUser user) {
//        user.setUpdateTime(new Date());
        return userMapper.updateUser(user);
    }


    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(SysUser user) {
        user.setUpdateTime(new Date());
        return updateUserInfo(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }


    /**
     * 校验登录名称是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    @Override
    public boolean checkLoginNameValided(String loginName) {
         loginName = loginName.trim();
        Pattern pattern = Pattern.compile("[a-zA-Z]*");
        Matcher isNum = pattern.matcher(loginName.charAt(0)+"");
        return isNum.matches();
    }


    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (user.isAdmin()) {
            throw new BusinessException("不允许操作超级管理员用户");
        }
    }

    /**
     * 查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId) {
        List<SysRole> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }


    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(Md5Utils.hash(user.getLoginName() + "123456"));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 导入成功");
                } else if (isUpdateSupport) {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getLoginName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 用户状态修改
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int changeStatus(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 统计当前财务分支下的用户数量
     *
     * @param supplierBranchId 财务分支ID
     * @return 结果
     */
    @Override
    public int countUserByBranchId(long supplierBranchId) {
        int iCount = userMapper.countUserByBranchId(supplierBranchId);
        return iCount;
    }

    /**
     * 删除当前财务分支下的所有用户、用户角色关联、银行卡角色关联信息
     *
     * @param supplierBranchId 财务分支ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleRelByBranchId(long supplierBranchId) {
        userMapper.deleteUserBy(supplierBranchId);
        //  userMapper.updateBankcardRoleBy(supplierBranchId);
        userMapper.deleteUserRoleBy(supplierBranchId);
        return 1;
    }
}
