package org.zaber.common.bloom;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 存在于布隆过滤器里的不一定是黑名单，但不存在的一定是白名单；降低 db 压力；
 * 1. 所以订单号存入布隆 2. 如果订单打过来 2. 布隆提示没有  3. 肯定没有
 * 4.布隆提示有 5.可能有、也可能没有 6. 再去数据库里找
 * 假阳性
 * @author : otter
 */
@Configuration
public class BloomFilterConfig {

    @Autowired
    private RedissonClient redissonClient;

    @Bean(name = "ipBlockBloomFilter")
    RBloomFilter getBloomFilter() {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("ipBlock");
        bloomFilter.tryInit(100000L, 0.03D);
        bloomFilter.add("127.0.0.1");
        return bloomFilter;
    }
}
