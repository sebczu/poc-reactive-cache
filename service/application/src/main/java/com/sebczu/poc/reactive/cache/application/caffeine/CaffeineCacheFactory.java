package com.sebczu.poc.reactive.cache.application.caffeine;

import com.sebczu.poc.reactive.cache.application.caffeine.asyncloading.CaffeineAsyncLoadingCache;
import com.sebczu.poc.reactive.cache.application.caffeine.asyncloading.CaffeineAsyncLoadingCacheProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaffeineCacheFactory {

  public static <K, V> CaffeineAsyncLoadingCache<K, V> caffeineAsyncLoadingCache(CaffeineAsyncLoadingCacheProperties<K, V> properties) {
    return new CaffeineAsyncLoadingCache<>(properties);
  }

}
