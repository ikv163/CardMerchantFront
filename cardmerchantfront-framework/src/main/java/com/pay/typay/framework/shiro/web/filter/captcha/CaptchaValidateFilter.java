package com.pay.typay.framework.shiro.web.filter.captcha;

import com.google.code.kaptcha.Constants;
import com.pay.typay.common.constant.ShiroConstants;
import com.pay.typay.common.utils.StringUtils;
import com.pay.typay.framework.util.ShiroUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证码过滤器
 *
 * @author js-oswald
 */
public class CaptchaValidateFilter extends AccessControlFilter {
    /**
     * 是否开启验证码
     */
    private boolean captchaEnabled = true;    /**
     * SN密钥开关
     */
    private boolean googlecodeEnabled = false;

    /**
     * 验证码类型
     */
    private String captchaType = "math" ;
    public boolean isGooglecodeEnabled() {
        return googlecodeEnabled;
    }

    public void setGooglecodeEnabled(boolean googlecodeEnabled) {
        this.googlecodeEnabled = googlecodeEnabled;
    }
    public void setCaptchaEnabled(boolean captchaEnabled) {
        this.captchaEnabled = captchaEnabled;
    }

    public void setCaptchaType(String captchaType) {
        this.captchaType = captchaType;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        request.setAttribute(ShiroConstants.CURRENT_ENABLED, captchaEnabled);
        request.setAttribute(ShiroConstants.CURRENT_TYPE, captchaType);
        request.setAttribute(ShiroConstants.GOOGLECODE_ENABLED, googlecodeEnabled);
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 验证码禁用 或不是表单提交 允许访问
        if (captchaEnabled == false || !"post".equals(httpServletRequest.getMethod().toLowerCase())) {
            return true;
        }
        return validateResponse(httpServletRequest, httpServletRequest.getParameter(ShiroConstants.CURRENT_VALIDATECODE));
    }

    public boolean validateResponse(HttpServletRequest request, String validateCode) {
        Object obj = ShiroUtils.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String code = String.valueOf(obj != null ? obj : "");
        if (StringUtils.isEmpty(validateCode) || !validateCode.equalsIgnoreCase(code)) {
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        request.setAttribute(ShiroConstants.CURRENT_CAPTCHA, ShiroConstants.CAPTCHA_ERROR);
        return true;
    }


}
