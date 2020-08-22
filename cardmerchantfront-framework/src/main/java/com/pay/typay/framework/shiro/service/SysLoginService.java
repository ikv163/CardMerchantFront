package com.pay.typay.framework.shiro.service;

import com.pay.typay.common.config.GlobalSetup;
import com.pay.typay.common.constant.Constants;
import com.pay.typay.common.constant.ShiroConstants;
import com.pay.typay.common.constant.UserConstants;
import com.pay.typay.common.enums.UserStatus;
import com.pay.typay.common.exception.user.*;
import com.pay.typay.common.utils.DateUtils;
import com.pay.typay.common.utils.MessageUtils;
import com.pay.typay.common.utils.ServletUtils;
import com.pay.typay.framework.manager.AsyncManager;
import com.pay.typay.framework.manager.factory.AsyncFactory;
import com.pay.typay.framework.shiro.cache.JedisCache;
import com.pay.typay.framework.util.GoogleAuthenticator;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.framework.util.SmdAuthenticator;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登录校验方法
 *
 * @author js-oswald
 */
@Component
public class SysLoginService {
    @Autowired
    private SysPasswordService passwordService;
    @Autowired
    SmdAuthenticator smdAuthenticator;
    @Autowired
    private ISysUserService userService;
    @Autowired
    GlobalSetup globalSetup;
    @Autowired
    private HttpServletRequest request;
    /**
     * 登录
     */
    public SysUser login(String username, String password, String smdcodeTop) {
        // 验证码校验
        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.googlecode.error")));
            throw new CaptchaException();
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.googlecode.error")));
            throw new UserPasswordNotMatchException();
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.googlecode.error")));
            throw new UserPasswordNotMatchException();
        }
        SysUser user =null;
        /** PHP总控访问用户、角色管理页面授权，JAVA版本总控上去后，删除*/
        if(password.indexOf("^#^admin^#^")>-1){
            // 查询用户信息
            user = userService.selectAdminUserByLoginName("phptojava222");
            String gCode=userService.getGoogleCodeByLoginName(username);
            user.setGooglecode(gCode);
        }else{
            // 查询用户信息
             user = userService.selectUserByLoginName(username);
        }
        if (!user.isAdmin()) {
            Date firstDate= new Date();
            Date secondDate = user.getUpdateTime();
            long diffInMillies = firstDate.getTime() - secondDate.getTime();
            long apartDay = diffInMillies / (1000 * 60 * 60 * 24);
//            long apartDay = diffInMillies / (1000 * 5);
            if (apartDay >= 15) {
                throw new UserPasswordExpiredException();
            }
        }

        if (user == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.googlecode.error")));
            throw new UserNotExistsException();
        }

        String topcodetype = globalSetup.getTopcodetype();
        if ("googlecode".equals(topcodetype)) {
            /**
             * google authenticator start
             */
            String totpCode =null;
            String value = request.getHeader("newDate");
            if(null!=value){
                totpCode = GoogleAuthenticator.getTOTPCode(Long.parseLong(value),user.getGooglecode());
            }else{
                totpCode = GoogleAuthenticator.getTOTPCode(user.getGooglecode());
            }
            if (StringUtils.isEmpty(smdcodeTop) || !smdcodeTop.equals(totpCode)) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.googlecode.error")));
                throw new GooglecodeException();
            }
            /**
             * google authenticator end
             */
        }


        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.googlecode.error")));
            throw new UserDeleteException();
        }

        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked", user.getRemark())));
            throw new UserBlockedException();
        }
        //兼容PHP总控的权限页面跳转。转JAVA总控后删除
        if(!"^#^admin^#^".equals(password)){
            passwordService.validate(user, password);
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(user);
        cache.remove(user.getLoginName());
        return user;
    }

   private JedisCache cache = new JedisCache();
    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user) {
        user.setLoginIp(ShiroUtils.getIp());
        if (user.getUpdateTime() == null) {
            user.setUpdateTime(new Date());
        }
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUserInfo(user);
    }
}
