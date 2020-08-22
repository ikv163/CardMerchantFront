package com.pay.typay.admin.controller.system;

import com.pay.typay.biz.dict.ITDictBankCommonService;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.constant.UserConstants;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.domain.Ztree;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.*;

import com.pay.typay.system.service.ISysRoleService;
import com.pay.typay.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色信息
 *
 * @author js-oswald
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
    private String prefix = "system/role";

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserService userService;

    @RequiresPermissions("system:role:view")
    @GetMapping()
    public String role() {
        return prefix + "/role";
    }

    @RequiresPermissions("system:role:view")
    @GetMapping("/g-role")
    @RequiresRoles("admin")
    public String grole() {
        return prefix + "/g-role";
    }

    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysRole role) {
        startPage();
        List<SysRole> list = roleService.selectRoleList(role);
        return getDataTable(list);
    }

    @RequiresPermissions("system:role:list")
    @PostMapping("/g-list")
    @RequiresRoles("admin")
    @ResponseBody
    public TableDataInfo glist(SysRole role) {
        startPage();
        List<SysRole> list = roleService.selectRoleList(role);
        return getDataTable(list);
    }


    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:role:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysRole role) {
        List<SysRole> list = roleService.selectRoleList(role);
        ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
        return util.exportExcel(list, "角色数据");
    }

    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增角色
     */
    @GetMapping("/g-add")
    @RequiresRoles("admin")
    public String gadd() {
        return prefix + "/g-add";
    }

    /**
     * 新增保存角色
     */
//    @RequiresPermissions("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysRole role) {
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        role.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
        return toAjax(roleService.insertRole(role));

    }

    /**
     * 新增保存角色
     */
//    @RequiresPermissions("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @RequiresRoles("admin")
    @PostMapping("/g-add")
    @ResponseBody
    public AjaxResult gaddSave(@Validated SysRole role) {
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        role.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
        return toAjax(roleService.insertRole(role));

    }

    /**
     * 修改角色
     */
    @Autowired
    ITDictBankCommonService itDictBankCommonService;


    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        SysRole sysRole = roleService.selectRoleById(roleId);
        mmap.put("role", sysRole);
        return prefix + "/edit";
    }

    /**
     * 修改角色
     */
    @GetMapping("/g-edit/{roleId}")
    @RequiresRoles("admin")
    public String gedit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        SysRole sysRole = roleService.selectRoleById(roleId);
        mmap.put("role", sysRole);
        return prefix + "/g-edit";
    }

    /**
     * 修改角色
     */
    @GetMapping("/sub-proview/{roleId}")
    public String subgedit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        SysRole sysRole = roleService.selectRoleById(roleId);
        mmap.put("role", sysRole);
        return prefix + "/sub-proview";
    }

    /**
     *
     */
    @GetMapping("/add/{parentId}")
    public String addparentId(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleById(parentId));
        return prefix + "/add";
    }


    /**
     * 修改保存角色
     */
//    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysRole role) {
        roleService.checkRoleAllowed(role);
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        role.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
        return toAjax(roleService.updateRole(role));
    }

    /**
     * 修改保存角色
     */
//    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @RequiresRoles("admin")
    @PostMapping("/g-edit")
    @ResponseBody
    public AjaxResult geditSave(@Validated SysRole role) {
        roleService.checkRoleAllowed(role);
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        role.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
        return toAjax(roleService.updateRole(role));
    }

    /**
     * 修改保存角色
     */
//    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/sub-edit")
    @ResponseBody
    public AjaxResult subSave(@Validated SysRole role) {
        roleService.checkRoleAllowed(role);
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        role.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
        return toAjax(roleService.subupdateRole(role));
    }

    /**
     * 角色分配数据权限
     */
    @GetMapping("/authDataScope/{roleId}")
    public String authDataScope(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/dataScope";
    }


    @RequiresPermissions("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(roleService.deleteRoleByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @RequiresPermissions("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{id}")
    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult removeByid(@PathVariable("id") String id) {
        try {
            return toAjax(roleService.deleteRoleByIds(id));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验角色名称
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public String checkRoleNameUnique(SysRole role) {
        return roleService.checkRoleNameUnique(role);
    }

    /**
     * 校验角色权限
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public String checkRoleKeyUnique(SysRole role) {
        return roleService.checkRoleKeyUnique(role);
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree")
    public String selectMenuTree() {
        return prefix + "/tree";
    }

    /**
     * 角色状态修改
     */
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
//    @RequiresPermissions("system:role:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysRole role) {
        roleService.checkRoleAllowed(role);
        return toAjax(roleService.changeStatus(role));
    }

    /**
     * 分配用户
     */
//    @RequiresPermissions("system:role:edit")
    @GetMapping("/authUser/{roleId}")
    public String authUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/authUser";
    }

    /**
     * 查询已分配用户角色列表
     */
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/allocatedList")
    @ResponseBody
    public TableDataInfo allocatedList(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectAllocatedList(user);
        return getDataTable(list);
    }

    /**
     * 取消授权
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancel")
    @ResponseBody
    public AjaxResult cancelAuthUser(SysUserRole userRole) {
        return toAjax(roleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancelAll")
    @ResponseBody
    public AjaxResult cancelAuthUserAll(Long roleId, String userIds) {
        return toAjax(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 选择用户
     */
    @GetMapping("/authUser/selectUser/{roleId}")
    public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/selectUser";
    }

    /**
     * 查询未分配用户角色列表
     */
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/unallocatedList")
    @ResponseBody
    public TableDataInfo unallocatedList(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUnallocatedList(user);
        return getDataTable(list);
    }

    /**
     * 批量选择用户授权
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/selectAll")
    @ResponseBody
    public AjaxResult selectAuthUserAll(Long roleId, String userIds) {
        return toAjax(roleService.insertAuthUsers(roleId, userIds));
    }


    /**
     * 加载所有菜单列表树
     */
    @GetMapping("/menuTreeData")
    @ResponseBody
    public List<Ztree> roleTreeData() {
        List<Ztree> ztrees = roleService.roleTreeData();
        return ztrees;
    }

    /**
     * 加载所有菜单列表树
     */
    @GetMapping("/menuTreeDataMultiple")
    @ResponseBody
    public List<Ztree> roleTreeDataMultiple() {

        List<Ztree> ztrees = roleService.roletreeDataMultiple();
        return ztrees;
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree/{menuId}")
    public String selectMenuTree(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleById(menuId));
        return prefix + "/tree";
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTreeMultipleChoose/{menuId}")
    public String selectMenuTreeMultipleChoose(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleMultipleChooseById(menuId));
        return prefix + "/treemultiplechoose";
    }
}