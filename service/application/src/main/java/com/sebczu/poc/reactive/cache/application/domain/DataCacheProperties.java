package com.sebczu.poc.reactive.cache.application.domain;

import com.sebczu.poc.reactive.cache.application.caffeine.asyncloading.CaffeineCacheProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties("data.cache")
public class DataCacheProperties extends CaffeineCacheProperties {


}
