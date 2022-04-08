package com.sebczu.poc.reactive.cache.application.domain;

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
@RequestMapping("/data")
public class DataRestController {

  private final CaffeineAsyncLoadingCache<Integer, String> dataCache;

  @GetMapping("/{id}")
  public Mono<String> data(@PathVariable  int id) {
    return dataCache.get(id);
  }

}
