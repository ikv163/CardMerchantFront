package com.pay.typay.admin.controller.system;

import com.pay.typay.biz.dict.GoogleCodeService;
import com.pay.typay.biz.dict.ITDictBankCommonService;
import com.pay.typay.biz.messages.ConstantsSelectUI;
import com.pay.typay.biz.messages.TranslateKeyConstant;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.annotation.Translate;
import com.pay.typay.common.annotation.TranslateKey;
import com.pay.typay.common.config.GlobalSetup;
import com.pay.typay.common.constant.UserConstants;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.core.page.TableDataInfo;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.exception.BusinessException;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.common.utils.mango.MangoUtils;
import com.pay.typay.common.utils.poi.ExcelUtil;
import com.pay.typay.common.utils.telegram.TelegramUtils;
import com.pay.typay.framework.shiro.service.SysPasswordService;
import com.pay.typay.framework.util.GoogleAuthenticator;
import com.pay.typay.framework.util.PHPpassword;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.ISysRoleService;
import com.pay.typay.system.service.ISysUserService;
import com.pay.typay.utils.UtilsUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户信息
 *
 * @author js-oswald
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    private String prefix = "system/user";


    @Autowired
    private TelegramUtils telegramUtils;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    ITDictBankCommonService itDictBankCommonService;

    @Autowired
    private SysPasswordService passwordService;

    @Value("${telegram.chatId}")
    private String telegarmChatId;

    @Value("${telegram.token}")
    private String telegarmToken;

    @Autowired
    private MangoUtils mangoUtils;

    @Value("${mango.userId}")
    private String userId;

    @Value("${mango.token}")
    private String mangoToken;

    @Value("${mango.roomname}")
    private String roomName;



    @RequiresPermissions("system:user:view")

    @GetMapping()
//    @RequiresRoles("admin")
    public String user() {
        return prefix + "/user";
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/g-user")
    @RequiresRoles("admin")
    public String guser() {
        return prefix + "/g-user";
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
//    @RequiresRoles("admin")
    @ResponseBody
    public TableDataInfo list(SysUser user) {
        startPage();
//        if(UtilsUser.getUserId() != 1) {
            user.setSupplierbranchid(ShiroUtils.getSupplierbranchid());
//            Long userSupplierbranchid = UtilsUser.getUserSupplierbranchid();
//            SysUser sysUser = ShiroUtils.getSysUser();
//            if (sysUser.isAdmin()) {  //如果是管理员账号
//                String supplierBranchIdGroup = UtilsUser.getSupplierBranchIdGroup();
//                List<String> sList = Arrays.asList(supplierBranchIdGroup.split(","));
//                user.setSupplierBranchIdGroupList(sList);
//                user.setSupplierbranchid(userSupplierbranchid);
//            } else {
//                user.setSupplierbranchid(userSupplierbranchid);
//            }
//        }
        List<SysUser> list = userService.selectUserListFront(user);
        list.stream().forEach(v -> v.setLockStatus(passwordService.getLockStatus(v.getLoginName())));
        return getDataTable(list);
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/g-list")
    @ResponseBody
    @RequiresRoles("admin")
    public TableDataInfo glist(SysUser user) {
        startPage();
        SysUser sysUser = ShiroUtils.getSysUser();
        List<SysUser> list = new ArrayList<>();
        if (sysUser.isAdmin()) {
            user.setRoleId(1L);
            list = userService.selectUserList(user);
        }
        return getDataTable(list);
    }


    @Log(title = "导出用户", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult export(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }

    @Log(title = "导入用户", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据", "");
    }

    /**
     * 新增用户
     */
    @GetMapping("/add/{parentId}")
    @RequiresRoles("admin")
    public String addparentId(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        mmap.put("roles", roleService.selectRoleAll());
        mmap.put("parentId", parentId);
        return prefix + "/add";
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    @RequiresRoles("admin")
    public String add(ModelMap mmap) {
        mmap.put("roles", roleService.selectRoleAll());
        return prefix + "/add";
    }

    /**
     * 新增用户
     */
    @GetMapping("/g-add")
    @RequiresRoles("admin")
    public String gadd(ModelMap mmap) {
        mmap.put("roles", roleService.selectRoleAll());
        return prefix + "/g-add";
    }

    @Autowired
    GlobalSetup globalSetup;

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "新增用户", businessType = BusinessType.INSERT,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @PostMapping("/add")
    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult addSave(@Validated SysUser user) {
        SysUser sysUser = ShiroUtils.getSysUser();
        if (!userService.checkLoginNameValided(user.getLoginName())) {
            return error("新增用户'" + user.getLoginName() + "'失败，请以字母开头的命名方式");
        }
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(user.getLoginName()))) {
            return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
        }
        if(!StringUtils.isEmpty(user.getPassword())) {
            if (!UtilsUser.checkPassWord(user.getPassword())) {
                return error("请重新输入(必须包含8-20位数字与字母，有英文大写)");
            }
        }
        Long supplierbranchid = user.getSupplierbranchid();
        if (supplierbranchid == null) {
            user.setSupplierbranchid(0L);
        } else {
            user.setSupplierbranchid(supplierbranchid);
        }

       // user.setSupplierbranchidgroup(StringUtils.trimReduut(user.getSupplierbranchidgroup(), ","));
        user.setPassword(PHPpassword.PHPpasswordHash(user.getPassword()));
        user.setCreateBy(ShiroUtils.getLoginName());
        user.setUpdateTime(new Date());
        String topcodetype = globalSetup.getTopcodetype();
        if ("googlecode".equals(topcodetype)) {
            user.setGooglecode(GoogleAuthenticator.generateSecretKey());
        }
        int  count = userService.insertUser(user);
        if(count > 0){
            String sendMsg = sysUser.getLoginName()+"新增卡分销用户"+"【" + user.getLoginName()+ "】";
            mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
        }
        return toAjax(count);
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "新增用户", businessType = BusinessType.INSERT,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @PostMapping("/g-add")
    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult gaddSave(@Validated SysUser user) {
        SysUser sysUser = ShiroUtils.getSysUser();
        if (!userService.checkLoginNameValided(user.getLoginName())) {
            return error("新增用户'" + user.getLoginName() + "'失败，请以字母开头的命名方式");
        }
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(user.getLoginName()))) {
            return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
        }
        Long supplierbranchid = user.getSupplierbranchid();
        if (supplierbranchid == null) {
            user.setSupplierbranchid(0L);
        }
        if(!StringUtils.isEmpty(user.getPassword())) {
            if (!UtilsUser.checkPassWord(user.getPassword())) {
                return error("请重新输入(必须包含8-20位数字与字母，有英文大写)");
            }
        }
        user.setSupplierbranchidgroup(StringUtils.trimReduut(user.getSupplierbranchidgroup(), ","));
        user.setPassword(PHPpassword.PHPpasswordHash(user.getPassword()));
        user.setCreateBy(ShiroUtils.getLoginName());
        user.setUpdateTime(new Date());
        String topcodetype = globalSetup.getTopcodetype();
        if ("googlecode".equals(topcodetype)) {
            user.setGooglecode(GoogleAuthenticator.generateSecretKey());
        }

        int  count = userService.insertUser(user);
        if(count > 0){
            String sendMsg = sysUser.getLoginName()+"新增卡分销用户"+"【" + user.getLoginName()+ "】";
            mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
        }
        return toAjax(count);
    }


    /**
     * 修改用户
     */
    @RequiresRoles("admin")
    @GetMapping("/edit/{userId}")
    @RequiresPermissions("system:user:list")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改用户
     */
    @GetMapping("/g-edit/{userId}")
    @RequiresRoles("admin")
    @RequiresPermissions("system:user:list")
    public String gedit(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        return prefix + "/g-edit";
    }

    @Autowired
    private GoogleCodeService googleCodeService;

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "修改用户",serviceName = "sysUserServiceImpl",methodName = "selectUserById",id = "userId",businessType = BusinessType.UPDATE,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @PostMapping("/edit")
    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult editSave(@Validated SysUser user) {
        SysUser sysUser = ShiroUtils.getSysUser();
        if (!sysUser.isAdmin()) {
            throw new BusinessException("此操作需要管理员权限");
        }

        try {
            googleCodeService.verifyGooglecode(user.getVerifycode());
        } catch (Exception e) {
            return error("验证码错误");
        }
        if(!StringUtils.isEmpty(user.getPassword())) {
            if (!UtilsUser.checkPassWord(user.getPassword())) {
                return error("请重新输入(必须包含8-20位数字与字母，有英文大写)");
            }
        }

        if (StringUtils.isNotEmpty(user.getSupplierbranchidgroup())) {
            String supplierbranchid = "" + user.getSupplierbranchid();
            String s = StringUtils.trimReduut(user.getSupplierbranchidgroup(), ",");
            boolean contain = StringUtils.isContain(s, supplierbranchid, ",");
            if (!contain) {
                user.setSupplierbranchid(0L);
            }
            user.setSupplierbranchidgroup(s);
        } else {
            user.setSupplierbranchid(user.getSupplierbranchid());
//            user.setSupplierbranchidgroup(supplierbranchid);
        }

        String googlecode = user.getGooglecode();
        if (StringUtils.isNotEmpty(googlecode)) {
            boolean verifyUserPassword = googleCodeService.isVerifyUserPassword(sysUser, user.getVerifedpassword());
            if (!verifyUserPassword) {
                return error("登录密码错误/login password is error");
            }
        }
        user.setUpdateBy(sysUser.getLoginName());
        int count = userService.updateUser(user);
        if(count > 0){
            String sendMsg = sysUser.getLoginName()+"修改卡分销用户"+"【" + user.getLoginName()+ "】";
            mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
        }
        return toAjax(count);
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "修改用户",serviceName = "sysUserServiceImpl",methodName = "selectUserById",id = "userId",businessType = BusinessType.UPDATE,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @PostMapping("/g-edit")
    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult geditSave(@Validated SysUser user) {
        SysUser sysUser = ShiroUtils.getSysUser();
        if (!sysUser.isAdmin()) {
            throw new BusinessException("此操作需要管理员权限");
        }

        try {
            googleCodeService.verifyGooglecode(user.getVerifycode());
        } catch (Exception e) {
            return error("验证码错误");
        }
        if(!StringUtils.isEmpty(user.getPassword())) {
            if (!UtilsUser.checkPassWord(user.getPassword())) {
                return error("请重新输入(必须包含8-20位数字与字母，有英文大写)");
            }
        }

        String supplierbranchid = "" + user.getSupplierbranchid();
        if (StringUtils.isNotEmpty(user.getSupplierbranchidgroup())) {
            String s = StringUtils.trimReduut(user.getSupplierbranchidgroup(), ",");
            boolean contain = StringUtils.isContain(s, supplierbranchid, ",");
            if (!contain) {
                user.setSupplierbranchid(0L);
            }
            user.setSupplierbranchidgroup(s);
        } else {
            user.setSupplierbranchid(0L);
            user.setSupplierbranchidgroup(supplierbranchid);
        }


        String googlecode = user.getGooglecode();
        if (StringUtils.isNotEmpty(googlecode)) {
            boolean verifyUserPassword = googleCodeService.isVerifyUserPassword(sysUser, user.getVerifedpassword());
            if (!verifyUserPassword) {
                return error("登录密码错误/login password is error");
            }
        }
        user.setUpdateBy(sysUser.getLoginName());
        int count =userService.updateUser(user);
        if(count > 0){
            String sendMsg = "【" + sysUser.getLoginName()+ "】"+"修改了"+"【" + user.getLoginName()+ "】密码";
            mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
        }
        return toAjax(count);
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "修改用户",serviceName = "sysUserServiceImpl",methodName = "selectUserById",id = "userId", businessType = BusinessType.UPDATE,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @PostMapping("/editbranch")
//    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult editbranch(SysUser user) {
        SysUser sysUser = ShiroUtils.getSysUser();
        user.setUserId(sysUser.getUserId());
        int i = userService.updateUserBranch(user);
        sysUser.setSupplierbranchid(user.getSupplierbranchid());
        ShiroUtils.setSysUser(sysUser);
        return toAjax(i);
    }


    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }


    @GetMapping("/sndisplay/{userId}")
    public String sndisplay(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/sn-display";
    }

    @PostMapping("/resetGoogleCode")
    @Log(title = "重置谷歌秘钥", businessType = BusinessType.UPDATE,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @ResponseBody
    public Map<String, Object> resetGoogleCode(SysUser user) {
        SysUser sysOpterUser = userService.selectUserById(user.getUserId());
        String currentLoginName = UtilsUser.getLoginName();
        //登录用户
        SysUser sysUser = ShiroUtils.getSysUser();
        //如果是管理员,并且,不是自己,就不可以修改
        Map<String,Object> modelMap = new HashMap<>();
        if (sysOpterUser.isAdmin()) {
            if (!currentLoginName.equals(sysOpterUser.getLoginName())) {
                modelMap.put("msg","禁止操作");
                modelMap.put("code","500");
                return modelMap;
            }
        }

        try {
            googleCodeService.verifyGooglecode(user.getVerifycode());
        } catch (Exception e) {
            return error(e.getMessage());
        }

        user.setGooglecode(GoogleAuthenticator.generateSecretKey());
        user.setUpdateTime(new Date());

        int i = userService.updateUserGoogleCode(user);
        if (currentLoginName.equals(sysOpterUser.getLoginName())) {
            sysUser.setGooglecode(user.getGooglecode());
            ShiroUtils.setSysUser(sysUser);
        }
        String googlecode = user.getGooglecode();
        modelMap.put("googlecode",googlecode);
        modelMap.put("msg","修改成功");
        modelMap.put("code","200");
        String sendMsg = "【" + sysUser.getLoginName()+ "】"+"重置了"+"【" + sysOpterUser.getLoginName()+ "】谷歌密钥";
        mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
        return modelMap;
    }


    @PostMapping("/refreshgooglecode")
    @Log(title = "重置谷歌秘钥", businessType = BusinessType.UPDATE,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @ResponseBody
    public AjaxResult refreshgooglecode(SysUser user) {
//        userService.checkUserAllowed(user);
        //登录用户
        SysUser sysUser = ShiroUtils.getSysUser();
        SysUser sysOpterUser = userService.selectUserById(user.getUserId());
        String currentLoginName = UtilsUser.getLoginName();
        //如果是管理员,并且,不是自己,就不可以修改
//        if (sysOpterUser.isAdmin()) {
//            if (!currentLoginName.equals(sysOpterUser.getLoginName())) {
//                return error("禁止操作");
//            }
//        }
        try {
            googleCodeService.verifyGooglecode(user.getVerifycode());
        } catch (Exception e) {
            return error(e.getMessage());
        }

        user.setGooglecode(GoogleAuthenticator.generateSecretKey());
        user.setUpdateTime(new Date());

        int i = userService.updateUserGoogleCode(user);
        String sendMsg = "【" + sysUser.getLoginName()+ "】"+"重置了"+"【" + user.getLoginName()+ "】谷歌密钥";
        mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
        if (currentLoginName.equals(sysOpterUser.getLoginName())) {
            sysUser.setGooglecode(user.getGooglecode());
            ShiroUtils.setSysUser(sysUser);
        }
        return toAjax(i);
    }


    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SysUser user) {
//        userService.checkUserAllowed(user);

        //登录用户
        SysUser sysUser = ShiroUtils.getSysUser();
        SysUser sysOpterUser = userService.selectUserById(user.getUserId());
        String currentLoginName = UtilsUser.getLoginName();
        //如果是管理员,并且,不是自己,就不可以修改
        if (sysOpterUser.isAdmin()) {
            if (!currentLoginName.equals(sysOpterUser.getLoginName())) {
                return error("禁止操作");
            }
        }
        try {
            googleCodeService.verifyGooglecode(user.getVerifycode());
        } catch (Exception e) {
            return error(e.getMessage());
        }
        if(!StringUtils.isEmpty(user.getPassword())) {
            if (!UtilsUser.checkPassWord(user.getPassword())) {
                return error("请重新输入(必须包含8-20位数字与字母，有英文大写)");
            }
        }
        user.setPassword(PHPpassword.PHPpasswordHash(user.getPassword()));
        if (userService.resetUserPwd(user) > 0) {
            if (ShiroUtils.getUserId().equals(user.getUserId())) {
                ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
            }
            String sendMsg = "【" + sysUser.getLoginName()+ "】"+"重置了"+"【" + user.getLoginName()+ "】密码";
            mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
            return success();
        }
        return error();
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "删除用户", businessType = BusinessType.DELETE,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @PostMapping("/remove")
    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(userService.deleteUserByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @GetMapping("/remove/{id}")
    @Log(title = "删除用户", businessType = BusinessType.DELETE,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @RequiresRoles("admin")
    @ResponseBody
    public AjaxResult removeByid(@PathVariable("id") String id) {
        try {
            return toAjax(userService.deleteUserByIds(id));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(SysUser user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }


    /**
     * 用户状态修改
     */
    @Log(title = "修改用户状态", businessType = BusinessType.UPDATE,
            translate = {@Translate(filed = "status",data = TranslateKeyConstant.userStatus)},
            translateKeys = {@TranslateKey(filedData = TranslateKeyConstant.User_Info_Key)})
    @RequiresPermissions("system:user:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxResult changeStatus(SysUser user) {
//        userService.checkUserAllowed(user);
        SysUser sysUser = ShiroUtils.getSysUser();
        String sendMsg = sysUser.getLoginName()+"【" + user.getLoginName()+ "】"+"修改用户状态";
        mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
        return toAjax(userService.changeStatus(user));
    }


    /**
     * 同步复制V1版本的用户、用户角色权限、卡角色权限
     */
    @GetMapping("/branch-user")
    @RequiresPermissions("system:user:add")
    public String branchUser(ModelMap mmap) {
        return prefix + "/branch-user";
    }

    /**
     * 同步复制V1版本的用户、用户角色权限、卡角色权限
     */
    @GetMapping("/cloneUser")
    @RequiresPermissions("system:user:add")
    public String cloneUser(ModelMap mmap) {
        return prefix + "/clone-user";
    }


    /**
     * 统计财务分支下的账号数
     */
    @GetMapping("/checkUserBy/{supplierBranchId}")
    @ResponseBody
    public AjaxResult checkUserBy(@PathVariable("supplierBranchId") Long supplierBranchId) {
        int iCount = userService.countUserByBranchId(supplierBranchId);
        return AjaxResult.success(iCount);
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/queryUserListBy")
    @ResponseBody
    public TableDataInfo queryUserListBy(SysUser user) {
        startPage();
        List<SysUser> list = userService.queryUserListBy(user.getSupplierbranchid());
        return getDataTable(list);
    }
}
