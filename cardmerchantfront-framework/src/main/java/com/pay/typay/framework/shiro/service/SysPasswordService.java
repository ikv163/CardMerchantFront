package com.pay.typay.framework.shiro.service;

import com.pay.typay.common.constant.Constants;
import com.pay.typay.common.constant.ShiroConstants;
import com.pay.typay.common.exception.user.UserPasswordNotMatchException;
import com.pay.typay.common.exception.user.UserPasswordRetryLimitExceedException;
import com.pay.typay.common.utils.MessageUtils;
import com.pay.typay.framework.manager.AsyncManager;
import com.pay.typay.framework.manager.factory.AsyncFactory;
import com.pay.typay.framework.util.PHPpassword;
import com.pay.typay.system.domain.SysUser;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录密码方法
 *
 * @author js-oswald
 */
@Component
public class SysPasswordService {
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

    @PostConstruct
    public void init() {
        loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
    }

    public void validate(SysUser user, String password) {
        String loginName = user.getLoginName();
        AtomicInteger retryCountArray = null;
        try {
            retryCountArray =  loginRecordCache.get(loginName);
        } catch (Exception e) {

        }

        AtomicInteger retryCount;
        if (retryCountArray == null) {
            retryCount = new AtomicInteger(0);
        } else {
            retryCount = new AtomicInteger(retryCountArray.get());
        }


        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        //if (!user.isAdmin()){
            if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue()) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
                throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
            }
        //}

        if (!matches(user, password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
            loginRecordCache.put(loginName, retryCount);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(SysUser user, String newPassword) {
        return PHPpassword.PHPpasswordVerify(newPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String username) {
        loginRecordCache.remove(username);
    }

    /**
     * 获取锁定状态
     * @param loginName
     * @return
     */
    public int getLockStatus(String loginName){
        Object o = loginRecordCache.get(loginName);
        AtomicInteger retryCount = null;
        if(o instanceof AtomicInteger){
            retryCount = loginRecordCache.get(loginName);
        }
        return retryCount == null ? 0 : retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue() ? 1 : 0;
    }

    public void unlock(String loginName) {
        loginRecordCache.remove(loginName);
    }

}
