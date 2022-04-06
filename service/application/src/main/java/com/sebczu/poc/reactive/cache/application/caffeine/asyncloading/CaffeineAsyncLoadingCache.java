package com.sebczu.poc.reactive.cache.application.caffeine.asyncloading;

import com.github.benmanes.caffeine.cache.*;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;

@Slf4j
public class CaffeineAsyncLoadingCache<K, V> {

  private final CaffeineAsyncLoadingCacheProperties<K, V> properties;
  private final AsyncLoadingCache<K, V> cache;

  public CaffeineAsyncLoadingCache(CaffeineAsyncLoadingCacheProperties<K, V> properties) {
    this.properties = properties;

    AsyncCacheLoader<K, V> loader = (key, executor) -> properties.getProvider().apply(key).toFuture();

    cache = Caffeine.newBuilder()
      .expireAfterWrite(Duration.ofSeconds(20))
      .maximumSize(1_000)
      .buildAsync(loader);
  }

  public Mono<V> get(K key) {
    if (properties.isEnabled()) {
      log.info("get from cache by key: {}", key);
      return Mono.fromFuture(cache.get(key));
    }
    return properties.getProvider().apply(key);
  }

}
