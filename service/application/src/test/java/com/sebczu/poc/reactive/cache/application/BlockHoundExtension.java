package com.sebczu.poc.reactive.cache.application;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import reactor.blockhound.BlockHound;

@Slf4j
public class BlockHoundExtension implements BeforeTestExecutionCallback {

  @Override
  public void beforeTestExecution(ExtensionContext extensionContext) {
    BlockHound.install(builder -> {
      log.info("BlockHound initialized");
    });
  }
}
