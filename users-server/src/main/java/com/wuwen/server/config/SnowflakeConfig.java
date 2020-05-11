package com.wuwen.server.config;

import com.wuwen.server.utils.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/** @author wuwen */
@Configuration
@Slf4j
public class SnowflakeConfig {

  private final StringRedisTemplate stringRedisTemplate;

  @Autowired
  public SnowflakeConfig(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }

  @Value("${spring.application.name}")
  private String serviceName;

  @Value("${server.port}")
  private String port;

  /** 雪花算法超时时间 */
  private static final long SNOWFLAKE_KEY_EXPIRE = 120000L;

  /** 雪花算法Redis锁 */
  private String snowflakeKey;

  private Snowflake snowflake;

  @Bean
  public Snowflake snowflakeId() throws UnknownHostException {
    long machineId = findMachineId();
    snowflake = new Snowflake(22L, machineId);
    return snowflake;
  }

  private long findMachineId() throws UnknownHostException {
    String address = InetAddress.getLocalHost().getHostAddress() + ":" + port;
    int maxMachineId = 31;
    for (int i = 0; i < maxMachineId; i++) {
      String key = serviceName + ":" + port;
      Boolean absent =
          stringRedisTemplate
              .opsForValue()
              .setIfAbsent(key, address, SNOWFLAKE_KEY_EXPIRE, TimeUnit.MILLISECONDS);
      if (Optional.ofNullable(absent).orElse(false)) {
        snowflakeKey = key;
        return i;
      }
    }

    throw new BeanInitializationException("snowflake machineId can not initial");
  }

  @Scheduled(fixedDelay = SNOWFLAKE_KEY_EXPIRE / 2)
  public void snowflakeHeart()
      throws UnknownHostException, NoSuchFieldException, IllegalAccessException {
    if (StringUtils.isBlank(snowflakeKey)) {
      return;
    }
    Boolean expire =
        stringRedisTemplate.expire(snowflakeKey, SNOWFLAKE_KEY_EXPIRE, TimeUnit.MILLISECONDS);
    if (expire == null || !expire) {
      log.error("snowflake machineId redis lock invalid");
      // 尝试重新注册
      long machineId = findMachineId();
      Field field = Snowflake.class.getDeclaredField("machineId");
      field.setAccessible(true);
      field.set(snowflake, machineId);
    }
  }
}
