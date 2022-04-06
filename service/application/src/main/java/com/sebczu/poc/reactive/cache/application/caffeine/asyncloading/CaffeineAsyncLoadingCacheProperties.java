package com.sebczu.poc.reactive.cache.application.caffeine.asyncloading;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CaffeineAsyncLoadingCacheProperties<K, V> {

  protected boolean enabled = true;
  protected Function<K, Mono<V>> provider;

}
