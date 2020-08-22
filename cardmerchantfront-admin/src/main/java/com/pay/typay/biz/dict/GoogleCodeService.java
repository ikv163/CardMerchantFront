package com.pay.typay.biz.dict;

import com.pay.typay.common.annotation.DataSource;
import com.pay.typay.common.config.GlobalSetup;
import com.pay.typay.common.enums.DataSourceType;
import com.pay.typay.common.exception.user.GooglecodeException;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.framework.util.GoogleAuthenticator;
import com.pay.typay.framework.util.PHPpassword;
import com.pay.typay.framework.util.ShiroUtils;
import com.pay.typay.framework.util.SmdAuthenticator;
import com.pay.typay.system.domain.SysUser;
import com.pay.typay.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 谷歌验证
 *
 * @author jsbucky
 * @date 2020-02-08
 */

@Service
public class GoogleCodeService {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    GlobalSetup globalSetup;
    @Autowired
    private ISysUserService userService;
    @Autowired
    SmdAuthenticator smdAuthenticator;

    /**
     * 校验用户的密码
     *
     * @param user
     * @param password
     * @return
     */
    public boolean isVerifyUserPassword(SysUser user, String password) {
        if (user != null && StringUtils.isNotEmpty(password)) {
            return matches(user,password);
        }
        return false;
    }
    public boolean matches(SysUser user, String newPassword) {
        return PHPpassword.PHPpasswordVerify(newPassword, user.getPassword());
    }


    @DataSource(DataSourceType.typayv2)
    public void verifyGooglecode(String googlecode) throws GooglecodeException {
        SysUser user = ShiroUtils.getSysUser();
        String topcodetype = globalSetup.getTopcodetype();
        if ("googlecode".equals(topcodetype)) {
            String value = request.getHeader("newDate");
            String totpCode =null;
            if(null!=value){
                totpCode = GoogleAuthenticator.getTOTPCode(Long.parseLong(value),user.getGooglecode());
            }else{
                totpCode = GoogleAuthenticator.getTOTPCode(user.getGooglecode());
            }
            if (org.springframework.util.StringUtils.isEmpty(googlecode) || !googlecode.equals(totpCode)) {
                throw new GooglecodeException();
            }
        }

    }

}
