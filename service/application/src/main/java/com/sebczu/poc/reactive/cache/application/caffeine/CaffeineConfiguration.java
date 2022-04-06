package com.sebczu.poc.reactive.cache.application.caffeine;

import com.sebczu.poc.reactive.cache.application.caffeine.asyncloading.CaffeineAsyncLoadingCache;
import com.sebczu.poc.reactive.cache.application.caffeine.asyncloading.CaffeineAsyncLoadingCacheProperties;
import com.sebczu.poc.reactive.cache.application.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CaffeineConfiguration {

  @Autowired
  private final DataRepository repository;

  @Bean
  public CaffeineAsyncLoadingCache<Integer, String> asyncloadingCache() {
    CaffeineAsyncLoadingCacheProperties<Integer, String> properties = new CaffeineAsyncLoadingCacheProperties<>();
    properties.setEnabled(true);
    properties.setProvider(repository::fetch);
    return CaffeineCacheFactory.caffeineAsyncLoadingCache(properties);
  }

}
