package com.sebczu.poc.reactive.cache.application.caffeine.asyncloading;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CaffeineCacheFactory {

  private final MeterRegistry registry;

  public <K, V> CaffeineAsyncLoadingCache<K, V> create(CaffeineCacheProperties properties,
                                                       Function<K, Mono<V>> provider) {
    return new CaffeineAsyncLoadingCache<>(properties, provider, registry);
  }

}
