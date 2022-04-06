package com.sebczu.poc.reactive.cache.application.caffeine;

import com.sebczu.poc.reactive.cache.application.caffeine.asyncloading.CaffeineAsyncLoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/caffeine")
public class CaffeineRestController {

  private final CaffeineAsyncLoadingCache<Integer, String> asyncloadingCache;

  @GetMapping("/asyncloading/{id}")
  public Mono<String> asyncloading(@PathVariable  int id) {
    return asyncloadingCache.get(id);
  }

}
