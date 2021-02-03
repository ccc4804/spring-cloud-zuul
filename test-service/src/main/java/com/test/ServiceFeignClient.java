package com.test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("service-b")
public interface ServiceFeignClient {
  @GetMapping(value = "/ping", consumes = "application/json")
  ResponseEntity<String> ping();
}
