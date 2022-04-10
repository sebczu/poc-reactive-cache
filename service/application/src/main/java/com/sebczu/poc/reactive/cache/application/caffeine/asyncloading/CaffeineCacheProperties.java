package com.sebczu.poc.reactive.cache.application.caffeine.asyncloading;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class CaffeineCacheProperties {

  protected String name;
  protected boolean enabled = true;
  protected Duration refreshAfterWrite;
  protected Integer initialCapacity;
  protected Long maximumSize;

}
