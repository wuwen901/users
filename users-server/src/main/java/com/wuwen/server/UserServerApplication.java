package com.wuwen.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/** @author wuwen */
@EnableJpaAuditing
@EnableConfigurationProperties
@EnableDiscoveryClient
@Configuration
@SpringBootApplication
public class UserServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserServerApplication.class, args);
  }
}
