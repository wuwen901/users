package com.wuwen.server.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wuwen.server.interceptor.AuthorizationInterceptor;
import com.wuwen.server.interceptor.HeaderArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;

/** @author wuwen */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

  @Bean
  public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
    return new JPAQueryFactory(entityManager);
  }

  @Resource private AuthorizationInterceptor authorizationInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authorizationInterceptor);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new HeaderArgumentResolver());
  }
}
