package com.sebczu.poc.reactive.cache.application.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
public class DataRepository {

  public Mono<String> fetch(Integer id) {
    return Mono.just(id)
      .delayElement(Duration.ofSeconds(4))
      .doOnNext(i -> {
        log.info("fetch id: {}", i);
      })
      .map(i -> i + "_data");
  }

}
