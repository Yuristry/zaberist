package org.zaber.common.bloom;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author : otter
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    RedissonClient getRedissonClient() {
        Config config = new Config();
        //单节点
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        if(StringUtils.isEmpty(password)){
            config.useSingleServer().setPassword(null);
        }else{
            config.useSingleServer().setPassword(password);
        }
        return Redisson.create(config);
    }
}
