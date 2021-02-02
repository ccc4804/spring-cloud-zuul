package com.test;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class Controller {
  @LoadBalanced private final WebClient.Builder webClient;

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

  /* annotaion을 통해 CircuitBreaker 기능 활성화
   * 만약 Circuit이 Open이 될 경우 fallback method 수행
   * default timeout : 1 sec
   * HystrixProperty : https://github.com/Netflix/Hystrix/wiki/Configuration
   */
  @HystrixCommand(
      commandKey = "info",
      fallbackMethod = "fallbackMethod",
      commandProperties = {
        @HystrixProperty(
            name = "execution.isolation.thread.timeoutInMilliseconds",
            value = "3000"), // set timeout value 3 sec
        @HystrixProperty(
            name = "circuitBreaker.errorThresholdPercentage",
            value = "10"), // error 비율이 10% 이상 발생하면 Circuit open
        @HystrixProperty(
            name = "circuitBreaker.requestVolumeThreshold",
            value = "2") // 2회 이상 호출되면 통계 시작
      })
  @GetMapping("/call")
  public ResponseEntity<String> call() {

    String response =
        webClient
            .build()
            .get()
            .uri("http://SERVICE-1/test/ping")
            .retrieve()
            .bodyToMono(String.class)
            .log()
            .block();

    return ResponseEntity.ok().body(response);
  }

  public ResponseEntity<String> fallbackMethod() {
    return ResponseEntity.badRequest().body("error");
  }
}
