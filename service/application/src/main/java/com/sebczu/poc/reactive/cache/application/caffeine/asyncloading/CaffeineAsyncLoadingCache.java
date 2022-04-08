package com.sebczu.poc.reactive.cache.application.caffeine.asyncloading;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class CaffeineAsyncLoadingCache<K, V> {

  private final AsyncLoadingCache<K, V> cache;
  private final Function<K, Mono<V>> provider;
  private final boolean enabled;

  public CaffeineAsyncLoadingCache(CaffeineCacheProperties properties, Function<K, Mono<V>> provider) {
    this.enabled = properties.isEnabled();
    this.provider = provider;

    AsyncCacheLoader<K, V> loader = (key, executor) -> provider.apply(key).toFuture();

    Caffeine caffeine = Caffeine.newBuilder();
    caffeine = setupInitialCapacity(caffeine, properties);
    caffeine = setupMaximumSize(caffeine, properties);

    cache = caffeine
      .refreshAfterWrite(properties.getRefreshAfterWrite())
      .buildAsync(loader);
  }

  private Caffeine<K, V> setupInitialCapacity(Caffeine<K, V> caffeine, CaffeineCacheProperties properties) {
    return Optional.ofNullable(properties.getInitialCapacity())
      .map(caffeine::initialCapacity)
      .orElse(caffeine);
  }

  private Caffeine<K, V> setupMaximumSize(Caffeine<K, V> caffeine, CaffeineCacheProperties properties) {
    return Optional.ofNullable(properties.getMaximumSize())
      .map(caffeine::maximumSize)
      .orElse(caffeine);
  }

  public Mono<V> get(K key) {
    if (enabled) {
      log.info("get from cache by key: {}", key);
      return Mono.fromFuture(cache.get(key));
    }
    return provider.apply(key);
  }

}
