package com.pay.typay.framework.shiro.web.filter.redirect;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author : aleck
 * @Description: 登录超时，http转https
 * @date : 2020年05月10日 12:50
 */
public class AbsoluteSendRedirectFilter extends OncePerRequestFilter {
    private String loginUrl="/login";
    /**  是否开启https  默认开启 */
    private  boolean   https;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RedirectResponseWrapper redirectResponseWrapper = new RedirectResponseWrapper(request, response,loginUrl,https);
        filterChain.doFilter(request, redirectResponseWrapper);
    }
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
    public void setHttps(boolean https) {
        this.https = https;
    }
}
