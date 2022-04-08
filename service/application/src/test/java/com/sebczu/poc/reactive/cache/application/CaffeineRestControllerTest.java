package com.sebczu.poc.reactive.cache.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(BlockHoundExtension.class)
public class CaffeineRestControllerTest {

  private static final String URL = "/data/cache/1";

  @Autowired
  private WebTestClient webClient;

  @Test
  void test() {
    webClient.get()
      .uri(URL)
      .exchange()
      .expectStatus().isOk();
  }

}
