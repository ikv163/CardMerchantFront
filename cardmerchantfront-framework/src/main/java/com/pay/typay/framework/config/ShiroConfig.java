package com.pay.typay.framework.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.pay.typay.framework.shiro.manager.JedisCacheManager;
import com.pay.typay.framework.shiro.realm.UserRealm;
import com.pay.typay.framework.shiro.session.OnlineSessionDAO;
import com.pay.typay.framework.shiro.session.RedisSessionDao;
import com.pay.typay.framework.shiro.web.filter.LogoutFilter;
import com.pay.typay.framework.shiro.web.filter.captcha.CaptchaValidateFilter;
import com.pay.typay.framework.shiro.web.filter.kickout.KickoutSessionFilter;
import com.pay.typay.framework.shiro.web.filter.online.OnlineSessionFilter;
import com.pay.typay.framework.shiro.web.filter.redirect.AbsoluteSendRedirectFilter;
import com.pay.typay.framework.shiro.web.session.OnlineWebSessionManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限配置加载
 *
 * @author js-oswald
 */
@Configuration
public class ShiroConfig {
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    // Session超时时间，单位为秒（默认30分钟）
    @Value("${shiro.session.expireTime}")
    private int expireTime;

    // 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
    @Value("${shiro.session.validationInterval}")
    private int validationInterval;

    // 同一个用户最大会话数
    @Value("${shiro.session.maxSession}")
    private int maxSession;

    // 踢出之前登录的/之后登录的用户，默认踢出之前登录的用户
    @Value("${shiro.session.kickoutAfter}")
    private boolean kickoutAfter;

//     验证码开关
    @Value("${shiro.user.captchaEnabled}")
    private boolean captchaEnabled;

    // SN密钥开关
    @Value("${shiro.user.googlecodeEnabled}")
    private boolean googlecodeEnabled;

    // 验证码类型
    @Value("${shiro.user.captchaType}")
    private String captchaType;

    // 设置Cookie的域名
    @Value("${shiro.cookie.domain}")
    private String domain;

    // 设置cookie的有效访问路径
    @Value("${shiro.cookie.path}")
    private String path;

    // 设置HttpOnly属性
    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;

    // 设置Cookie的过期时间，秒为单位
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

    // 登录地址
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    // 权限认证失败地址
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;
    // 默认开启HTTPS
    @Value("${shiro.user.https}")
    private boolean https=true;


    /**
     * 自定义Realm
     */
    @Bean
    public UserRealm userRealm(JedisCacheManager cacheManager) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(cacheManager);
        return userRealm;
    }
    @Bean
    public OnlineSessionDAO onlineSessionDAO() {
        OnlineSessionDAO onlineSessionDAO = new OnlineSessionDAO();
        onlineSessionDAO.setCacheManager(jedisCacheManager());
        return onlineSessionDAO;
    }

    /**
     * 自定义sessionDAO会话
     */
    @Bean
    public SessionDAO sessionDAO() {
        return new RedisSessionDao(expireTime);
    }



    //这里需要设置一个cookie的名称  原因就是会跟原来的session的id值重复的
    @Bean
    public SimpleCookie simpleCookie() {
        return new SimpleCookie("REDIS_SESSION");
    }

    @Bean
    public JedisCacheManager jedisCacheManager() {
        return new JedisCacheManager();
    }


    /**
     * 会话管理器
     */
    @Bean
    public OnlineWebSessionManager sessionManager() {
        OnlineWebSessionManager manager = new OnlineWebSessionManager();
        manager.setSessionIdCookie(simpleCookie());
        manager.setSessionDAO(sessionDAO());// 设置SessionDao
        manager.setDeleteInvalidSessions(true);// 删除过期的session
        manager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session
        manager.setSessionIdUrlRewritingEnabled(false);
        return manager;
    }

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(userRealm);
        // 记住我
        securityManager.setRememberMeManager(rememberMeManager());
        // 注入缓存管理器;
        // 自定义Cache实现;
        securityManager.setCacheManager(jedisCacheManager());
        // 自定义Ssession管理
        securityManager.setSessionManager(sessionManager());

        return securityManager;
    }

    /**
     * 退出过滤器
     */
    public LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setCacheManager(jedisCacheManager());
        logoutFilter.setLoginUrl(loginUrl);
        return logoutFilter;
    }

    /**
     * Shiro过滤器配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 对静态资源设置匿名访问
        filterChainDefinitionMap.put("/favicon.ico**", "anon");
        filterChainDefinitionMap.put("/ruoyi.png**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/docs/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/ajax/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/ruoyi/**", "anon");
        filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
        // 退出 logout地址，shiro去清除session
        filterChainDefinitionMap.put("/logout", "logout");
        // 不需要拦截的访问
        filterChainDefinitionMap.put("/login", "anon,captchaValidate");
        filterChainDefinitionMap.put("/external/**", "anon");
        filterChainDefinitionMap.put("/adminPage", "anon,captchaValidate");
        // 系统权限列表
        // filterChainDefinitionMap.putAll(SpringUtils.getBean(IMenuService.class).selectPermsAll());
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("absoluteSendRedirect", absoluteSendRedirectFilter());
        filters.put("onlineSession", onlineSessionFilter());
        filters.put("captchaValidate", captchaValidateFilter());
        filters.put("kickout", kickoutSessionFilter());
        // 注销成功，则跳转到指定页面
        filters.put("logout", logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 所有请求需要认证
        filterChainDefinitionMap.put("/**", "absoluteSendRedirect,user,kickout,onlineSession");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 自定义在线用户处理过滤器
     */
    @Bean
    public OnlineSessionFilter onlineSessionFilter() {
        OnlineSessionFilter onlineSessionFilter = new OnlineSessionFilter();
        onlineSessionFilter.setLoginUrl(loginUrl);
        return onlineSessionFilter;
    }
    /**
     * 自定义在线用户处理过滤器
     */
    @Bean
    public AbsoluteSendRedirectFilter absoluteSendRedirectFilter() {
        AbsoluteSendRedirectFilter absoluteSendRedirectFilter = new AbsoluteSendRedirectFilter();
        absoluteSendRedirectFilter.setLoginUrl(loginUrl);
        absoluteSendRedirectFilter.setHttps(https);

        return absoluteSendRedirectFilter;
    }

    /**
     * 自定义验证码过滤器
     */
    @Bean
    public CaptchaValidateFilter captchaValidateFilter() {
        CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
        captchaValidateFilter.setCaptchaEnabled(captchaEnabled);
        captchaValidateFilter.setGooglecodeEnabled(googlecodeEnabled);
        captchaValidateFilter.setCaptchaType(captchaType);
        return captchaValidateFilter;
    }

    /**
     * cookie 属性设置
     */
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge * 24 * 60 * 60);
        return cookie;
    }

    /**
     * 记住我
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("fCq+/xW488hMTCD+cmJ3aQ=="));
        return cookieRememberMeManager;
    }

    /**
     * 同一个用户多设备登录限制
     */
    public KickoutSessionFilter kickoutSessionFilter() {
        KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
        kickoutSessionFilter.setCacheManager(jedisCacheManager());
        kickoutSessionFilter.setSessionManager(sessionManager());
        // 同一个用户最大的会话数，默认-1无限制；比如2的意思是同一个用户允许最多同时两个人登录
        kickoutSessionFilter.setMaxSession(maxSession);
//         是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序
        kickoutSessionFilter.setKickoutAfter(kickoutAfter);
        // 被踢出后重定向到的地址；
        kickoutSessionFilter.setKickoutUrl("/login?kickout=1");
        return kickoutSessionFilter;
    }

    /**
     * thymeleaf模板引擎和shiro框架的整合
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 开启Shiro注解通知器
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
