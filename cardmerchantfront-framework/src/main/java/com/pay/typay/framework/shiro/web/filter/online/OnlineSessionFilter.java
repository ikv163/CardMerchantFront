package com.pay.typay.framework.shiro.web.filter.online;

import com.pay.typay.common.constant.ShiroConstants;
import com.pay.typay.common.enums.OnlineStatus;
import com.pay.typay.common.jedis.JedisConfig;
import com.pay.typay.framework.shiro.session.OnlineSession;
import com.pay.typay.framework.shiro.session.OnlineSessionDAO;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.JedisCluster;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 自定义访问控制
 *
 * @author js-oswald
 */
public class OnlineSessionFilter extends AccessControlFilter {
    private JedisCluster jedisCluster = JedisConfig.getjedisCluster();
    /**
     * 强制退出后重定向的地址
     */
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        Subject subject = getSubject(request, response);
        if (subject == null || subject.getSession() == null) {
            return true;
        }
        Session session = onlineSessionDAO.readSession(subject.getSession().getId());
        if (session != null && session instanceof OnlineSession) {
            OnlineSession onlineSession = (OnlineSession) session;
            request.setAttribute(ShiroConstants.ONLINE_SESSION, onlineSession);
            if (onlineSession.getStatus() == OnlineStatus.off_line) {
                return false;
            }
        }

        return true;
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (subject != null) {
            subject.logout();
        }
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    // 跳转到登录页
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.issueRedirect(request, response, loginUrl);
    }
}
