package com.pay.typay.framework.shiro.session;

import com.pay.typay.common.constant.Constants;
import com.pay.typay.common.jedis.JedisConfig;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;

/**
 * @ClassName RedisSessionDao
 * @Description
 * @Author JS-oswald
 * @Date 2020/2/21 下午6:05
 **/
public class RedisSessionDao extends CachingSessionDAO {
    private int expireTime;
    private static final String PREFIX = "SHIRO_SESSION_ID";
    private JedisCluster jedisCluster = JedisConfig.getjedisCluster();

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSessionDao.class);
    public RedisSessionDao(){

    }
    public RedisSessionDao(int expire){
        expireTime=expire*60;
    }
    @Override
    protected Serializable doCreate(Session session) {
        Serializable serializable = this.generateSessionId(session);
        assignSessionId(session, serializable);
        session.setTimeout(expireTime * 1000);
        jedisCluster.setex(getByteKey(serializable), expireTime, SerializationUtils.serialize((Serializable) session));
        return serializable;
    }


    @Override
    protected Session doReadSession(Serializable serializable) {
        if (serializable == null) {
            return null;
        }
        Session session = null;
        byte[] s = jedisCluster.get(getByteKey(serializable));
        if (s != null) {
            session = SerializationUtils.deserialize(s);
            jedisCluster.expire((PREFIX + serializable).getBytes(), expireTime);
        }
        //判断是否有会话  没有返回NULL
        if (session == null) {
            return null;
        }

        return session;
    }

    private byte[] getByteKey(Object k) {
        if (k instanceof String) {
            String key = PREFIX + k;
            return key.getBytes();
        } else {
            return SerializationUtils.serialize((Serializable) k);
        }
    }

    @Override
    protected void doUpdate(Session session) {
        if (session == null) {
            return;
        }
        session.setTimeout(expireTime * 1000);
        jedisCluster.setex(getByteKey(session.getId()), expireTime, SerializationUtils.serialize((Serializable) session));
    }


    @Override
    protected void doDelete(Session session) {
        jedisCluster.del(getByteKey(session.getId()));
    }
}
