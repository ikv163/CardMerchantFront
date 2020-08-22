package com.pay.typay.framework.shiro.web.filter.sync;

import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 同步Session数据到Db
 *
 * @author js-oswald
 */
public class SyncOnlineSessionFilter extends PathMatchingFilter {

    /**
     * 同步会话数据到DB 一次请求最多同步一次 防止过多处理 需要放到Shiro过滤器之前
     */
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return true;
    }
}
