package com.depli;

import java.util.concurrent.Executor;
import org.infinispan.spring.provider.SpringEmbeddedCacheManagerFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class DepliApplication extends AsyncConfigurerSupport {

  public static void main(String[] args) {
    SpringApplication.run(DepliApplication.class, args);
  }

  @Bean
  public SpringEmbeddedCacheManagerFactoryBean springCache() {
    return new SpringEmbeddedCacheManagerFactoryBean();
  }

  @Override
  public Executor getAsyncExecutor() {
    /* Configure and initialize async thread store */
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setCorePoolSize(10);
    threadPoolTaskExecutor.setMaxPoolSize(1000);
    threadPoolTaskExecutor.setQueueCapacity(500);
    threadPoolTaskExecutor.setThreadNamePrefix("AsyncExecutor");
    threadPoolTaskExecutor.initialize();

    return threadPoolTaskExecutor;
  }
}
