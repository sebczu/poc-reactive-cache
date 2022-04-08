package com.sebczu.poc.reactive.cache.application.domain;

import com.sebczu.poc.reactive.cache.application.caffeine.asyncloading.CaffeineAsyncLoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfiguration {

  @Bean
  public CaffeineAsyncLoadingCache<Integer, String> dataCache(DataRepository repository,
                                                              DataCacheProperties properties) {
    return new CaffeineAsyncLoadingCache<>(properties, repository::fetch);
  }

}
