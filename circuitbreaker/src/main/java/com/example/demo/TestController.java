package com.example.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	private Service service;

	@GetMapping("/test")
	public String getValues() {
		long start = System.currentTimeMillis();

		String fetchData = service.fetchData();
		System.out.print("Time taken : " + (System.currentTimeMillis() - start));
		System.out.println("---------------------------");
		return fetchData;
	}

	@GetMapping("/")
	public String index() {
		return new Date() + "";
	}
}
