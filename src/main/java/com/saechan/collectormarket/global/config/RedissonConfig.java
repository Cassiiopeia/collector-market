package com.saechan.collectormarket.global.config;

import java.io.IOException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

  @Value("${spring.data.redis.host}")
  private String host;

  @Value("${spring.data.redis.port}")
  private int port;

  @Value("${spring.data.redis.password}")
  private String password;

  @Bean
  public RedissonConnectionFactory redissonConnectionFactory(
      RedissonClient redisson) {
    return new RedissonConnectionFactory(redisson);
  }

  @Bean(destroyMethod = "shutdown")
  public RedissonClient redisson() throws IOException {
    Config config = new Config();
    config.useSingleServer()
//        .setAddress("redis://" + host + ":" + port)
        .setAddress("redis://" +"127.0.0.1" + ":" + port); //test
//        .setPassword(password);

    return Redisson.create(config);
  }
}
