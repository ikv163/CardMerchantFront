package com.pay.typay.common.config;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.redisson.config.ReadMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redisson配置类
 */
@ConfigurationProperties(prefix = "spring.redis.redisson")
@Data
public class RedissonProperties {

    private int threads = 0;
    private int nettyThreads = 0;
    private long lockWatchdogTimeout = 30 * 1000;

    private int timeout = 3000;
    private int pingTimeout = 1000;
    private int idleConnectionTimeout = 10000;
    private int retryAttempts = 3;
    private int retryInterval = 1500;

    private int scanInterval = 5000;
    private String readMode = ReadMode.SLAVE.name();
    private int slaveConnectionMinimumIdleSize = 32;
    private int slaveConnectionPoolSize = 64;
    private int failedSlaveReconnectionInterval = 3000;
    private int failedSlaveCheckInterval = 180000;
}
