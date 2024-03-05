package com.example.demo;

import java.time.LocalDateTime;

import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@org.springframework.stereotype.Service
public class Service {

	String serviceUrl = "http://localhost:8088/hello";

	@CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallback")
	@Retry(name = "vakeel")
	public String fetchData() {
		System.out.println(" Making a request to " + serviceUrl + " at :" + LocalDateTime.now());
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(serviceUrl, String.class);
	}

	public String fallback(Exception e) {
		return "fallback value";
	}

}
