package com.sebczu.poc.reactive.cache.application.caffeine.asyncloading;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineStatsCounter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class CaffeineAsyncLoadingCache<K, V> {

  private final AsyncLoadingCache<K, V> cache;
  private final Function<K, Mono<V>> provider;
  private final boolean enabled;

  CaffeineAsyncLoadingCache(CaffeineCacheProperties properties, Function<K, Mono<V>> provider, MeterRegistry registry) {
    this.enabled = properties.isEnabled();
    this.provider = provider;

    AsyncCacheLoader<K, V> loader = (key, executor) -> provider.apply(key).toFuture();
    CaffeineStatsCounter stats = new CaffeineStatsCounter(registry, properties.getName());

    Caffeine caffeine = Caffeine.newBuilder();
    caffeine = setupInitialCapacity(caffeine, properties);
    caffeine = setupMaximumSize(caffeine, properties);

    cache = caffeine
      .recordStats(() -> stats)
      .evictionListener(removeListener())
      .removalListener(removeListener())
      .executor(executor())
      .refreshAfterWrite(properties.getRefreshAfterWrite())
      .buildAsync(loader);

    stats.registerSizeMetric(cache.synchronous());
  }

  public Mono<V> get(K key) {
    if (enabled) {
      log.info("get from cache by key: {}", key);
      return Mono.fromFuture(cache.get(key));
    }
    return provider.apply(key);
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

  private RemovalListener<K, V> removeListener() {
    return (key, value, cause) -> {
      log.info("remove {}", key);
    };
  }

  private ExecutorService executor() {
    ThreadFactory cacheThreadFactory = new ThreadFactoryBuilder()
      .setNameFormat("cache-%d")
      .build();

    return Executors.newFixedThreadPool(5, cacheThreadFactory);
  }

}
