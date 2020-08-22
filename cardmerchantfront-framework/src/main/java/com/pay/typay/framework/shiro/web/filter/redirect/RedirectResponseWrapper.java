package com.pay.typay.framework.shiro.web.filter.redirect;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author : aleck
 * @Description: 登录超时，http转https
 * @date : 2020年05月10日 12:52
 */
@Slf4j
class RedirectResponseWrapper extends HttpServletResponseWrapper {
    /**
     * login
     */
    private String loginUrl;
    /**  默认开启https */
    private boolean https=true;
    private final HttpServletRequest request;

    public RedirectResponseWrapper(final HttpServletRequest inRequest, final HttpServletResponse response,String loginUrl,boolean https) {
        super(response);
        this.request = inRequest;
        this.loginUrl=loginUrl;
        this.https=https;
    }

    @Override
    public void sendRedirect(final String pLocation) throws IOException {
        String scheme=request.getScheme();
        log.info("login_http_type:{}",scheme);
        if (StringUtils.isBlank(pLocation)) {
            super.sendRedirect(pLocation);
            return;
        }else if(https && pLocation.equals(loginUrl)){
            String finalUrl =pLocation;
            try {
                final URI uri = new URI(pLocation);
                if (uri.getScheme() != null) {
                    super.sendRedirect(pLocation);
                    return;
                }
                finalUrl = "https://" + this.request.getServerName();
                if (request.getServerPort() != 80 && request.getServerPort() != 443) {
                    finalUrl += ":" + request.getServerPort();
                }
                finalUrl += pLocation;
            } catch (URISyntaxException ex) {
                super.sendRedirect(pLocation);
            }
            super.sendRedirect(finalUrl);
        }else{
            super.sendRedirect(pLocation);
        }
    }
}