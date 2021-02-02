package com.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/s1")
public class Controller {
private final ServiceFeignClient serviceFeignClient;


  @GetMapping
  public ResponseEntity<String> test() {
    return ResponseEntity.ok().body("success");
  }

  @GetMapping("/ping")
  public ResponseEntity<String> ping() {
    return ResponseEntity.ok().body("pong");
  }

  @GetMapping("/error")
  public ResponseEntity<String> error() {
    return ResponseEntity.ok().body("error");
  }

  @GetMapping("/call")
  public ResponseEntity<String> call() {
    return serviceFeignClient.ping();
  }

//  public ResponseEntity<String> fallbackMethod() {
//    return ResponseEntity.badRequest().body("error");
//  }
}
