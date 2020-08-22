package com.pay.typay.framework.shiro.session;

import com.pay.typay.common.enums.OnlineStatus;
import com.pay.typay.framework.shiro.service.SysShiroService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * 针对自定义的ShiroSession的db操作
 *
 * @author js-oswald
 */
public class OnlineSessionDAO extends EnterpriseCacheSessionDAO {

    @Autowired
    private SysShiroService sysShiroService;

    public OnlineSessionDAO() {
        super();
    }

    public OnlineSessionDAO(long expireTime) {
        super();
    }

    /**
     * 根据会话ID获取会话
     *
     * @param sessionId 会话ID
     * @return ShiroSession
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        return sysShiroService.getSession(sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        super.update(session);
    }

    /**
     * 当会话过期/停止（如用户退出时）属性等会调用
     */
    @Override
    protected void doDelete(Session session) {
        OnlineSession onlineSession = (OnlineSession) session;
        if (null == onlineSession) {
            return;
        }
        onlineSession.setStatus(OnlineStatus.off_line);
        sysShiroService.deleteSession(onlineSession);
    }
}
