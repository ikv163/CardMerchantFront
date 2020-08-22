package com.pay.typay.common.jedis;

import com.pay.typay.common.config.GlobalSetup;
import com.pay.typay.common.utils.spring.SpringUtils;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName JedisConfig
 * @Description
 * @Author JS-oswald
 * @Date 2020/2/21 下午5:33
 **/
public class JedisConfig extends CachingConfigurerSupport {
    private static int timeout = 6000;
    private static GlobalSetup globalSetup = SpringUtils.getBean(GlobalSetup.class);
    private static JedisCluster jedisCluster;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        String password = globalSetup.getPassword();
        int maxActive = globalSetup.getMaxActive();
        config.setMaxTotal(maxActive);
        int maxIdle = globalSetup.getMaxIdle();
        config.setMaxIdle(maxIdle);
        int minIdle = globalSetup.getMinIdle();
        config.setMinIdle(minIdle);//设置最小空闲数
        int maxWait = globalSetup.getMaxWait();
        config.setMaxWaitMillis(maxWait * 20);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        //Idle时进行连接扫描
        config.setTestWhileIdle(true);
        //表示idle object evitor两次扫描之间要sleep的毫秒数
        config.setTimeBetweenEvictionRunsMillis(30000);
        //表示idle object evitor每次扫描的最多的对象数
        config.setNumTestsPerEvictionRun(10);
        config.setTestOnBorrow(true);
        //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        config.setMinEvictableIdleTimeMillis(60000);
        String nodesaddressstr = globalSetup.getNodes();
        Set<HostAndPort> nodes = new HashSet<>();
        String[] split = nodesaddressstr.split(",");
        for (String s : split) {
            String[] split1 = s.split(":");
            nodes.add(new HostAndPort(split1[0], Integer.valueOf(split1[1])));
        }
        if ("".equals(password)) {
            jedisCluster = new JedisCluster(nodes, timeout, maxActive, 2000, config);
        } else {
            jedisCluster = new JedisCluster(nodes, timeout, maxActive, 2000, password, config);
        }
    }

    public static JedisCluster getjedisCluster() {
        return jedisCluster;
    }
}
