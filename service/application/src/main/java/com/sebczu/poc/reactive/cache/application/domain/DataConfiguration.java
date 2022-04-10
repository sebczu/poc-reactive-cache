package com.sebczu.poc.reactive.cache.application.domain;

import com.sebczu.poc.reactive.cache.application.caffeine.asyncloading.CaffeineAsyncLoadingCache;
import com.sebczu.poc.reactive.cache.application.caffeine.asyncloading.CaffeineCacheFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataConfiguration {

  private final CaffeineCacheFactory cacheFactory;

  @Bean
  public CaffeineAsyncLoadingCache<Integer, String> dataCache(DataRepository repository,
                                                              DataCacheProperties properties) {
    return cacheFactory.create(properties, repository::fetch);
  }

}
