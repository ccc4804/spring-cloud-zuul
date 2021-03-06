package com.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
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
}
