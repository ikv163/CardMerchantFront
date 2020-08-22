package com.pay.typay.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 全局配置类
 *
 * @author js-oswald
 */
@Data
@Component
public class GlobalSetup {
    @Value("${spring.topcodetype}")
    private  String topcodetype;

    @Value("${spring.redis.password}")
    private  String password;
    @Value("${spring.redis.timeout}")
    private  int timeout;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private  int maxWait;
    @Value("${spring.redis.jedis.pool.max-active}")
    private  int maxActive;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private  int maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private  int minIdle;
    @Value("${spring.redis.cluster.nodes}")
    private  String nodes;
}
