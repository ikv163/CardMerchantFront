package com.pay.typay.admin.controller.system;

import com.pay.typay.biz.dict.GoogleCodeService;
import com.pay.typay.common.annotation.Log;
import com.pay.typay.common.config.Global;
import com.pay.typay.common.core.controller.BaseController;
import com.pay.typay.common.core.domain.AjaxResult;
import com.pay.typay.common.enums.BusinessType;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.common.utils.file.FileUploadUtils;
import com.pay.typay.common.utils.mango.MangoUtils;
import com.pay.typay.common.utils.telegram.TelegramUtils;
import com.pay.typay.framework.shiro.service.SysPasswordService;
import com.pay.typay.framework.util.PHPpassword;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人信息 业务处理
 *
 * @author js-oswald
 */
@Controller
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SysProfileController.class);

    private String prefix = "system/user/profile" ;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private GoogleCodeService googleCodeService;

    @Autowired
    private TelegramUtils telegramUtils;

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

    /**
     * 个人信息
     */
    @GetMapping()
    public String profile(ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        //隐藏谷歌密钥
        user.setGooglecode("");
        mmap.put("user" , user);
        mmap.put("roleGroup" , userService.selectUserRoleGroup(user.getUserId()));
        return prefix + "/profile" ;
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        SysUser user = ShiroUtils.getSysUser();
        if (passwordService.matches(user, password)) {
            return true;
        }
        return false;
    }

    @GetMapping("/resetPwd")
    public String resetPwd(ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("user" , userService.selectUserById(user.getUserId()));
        return prefix + "/resetPwd" ;
    }

    @Log(title = "重置密码" , businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(String oldPassword, String newPassword,String googlecode) {
        //验证谷歌验证码
        try {
            googleCodeService.verifyGooglecode(googlecode);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        SysUser user = ShiroUtils.getSysUser();
        if (StringUtils.isNotEmpty(newPassword) && passwordService.matches(user, oldPassword)) {
            user.setPassword(PHPpassword.PHPpasswordHash(newPassword));
            if (userService.resetUserPwd(user) > 0) {
                ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
                String sendMsg = "【" + user.getLoginName()+ "】"+"重置了"+"【" + user.getLoginName()+ "】密码";
                mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
                return success();
            }
            return error();
        } else {
            return error("修改密码失败，旧密码错误");
        }
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit")
    public String edit(ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("user" , userService.selectUserById(user.getUserId()));
        return prefix + "/edit" ;
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("user" , userService.selectUserById(user.getUserId()));
        return prefix + "/avatar" ;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息" , businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult update(SysUser user) {
        SysUser currentUser = ShiroUtils.getSysUser();
        currentUser.setUserName(user.getUserName());
//        currentUser.setEmail(user.getEmail());
//        currentUser.setPhonenumber(user.getPhonenumber());
//        currentUser.setSex(user.getSex());
        if (userService.updateUserInfo(currentUser) > 0) {
            ShiroUtils.setSysUser(userService.selectUserById(currentUser.getUserId()));
            String sendMsg = "【" + user.getUserName()+ "】"+"修改了"+"【" + user.getUserName()+ "】用户信息";
            mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
            return success();
        }
        return error();
    }

    /**
     * 保存头像
     */
    @Log(title = "个人信息" , businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file) {
        SysUser currentUser = ShiroUtils.getSysUser();
        try {
            if (!file.isEmpty()) {
                String avatar = FileUploadUtils.upload(Global.getAvatarPath(), file);
                currentUser.setAvatar(avatar);
                if (userService.updateUserInfo(currentUser) > 0) {
                    String sendMsg = "【" + currentUser.getLoginName()+ "】"+"修改了头像";
                    mangoUtils.sendMessage(userId,sendMsg,roomName,mangoToken);
                    ShiroUtils.setSysUser(userService.selectUserById(currentUser.getUserId()));
                    return success();
                }
            }
            return error();
        } catch (Exception e) {
            log.error("修改头像失败！" , e);
            return error(e.getMessage());
        }
    }
}
