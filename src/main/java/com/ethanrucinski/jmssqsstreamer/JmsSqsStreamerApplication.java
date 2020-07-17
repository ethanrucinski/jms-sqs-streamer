package com.ethanrucinski.jmssqsstreamer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.ComponentScan; 

import org.springframework.stereotype.Component;


@SpringBootApplication @RestController
@ComponentScan("com.ethanrucinski.jmssqsstreamer.*")
@Component
public class JmsSqsStreamerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmsSqsStreamerApplication.class, args);
		
	}

	// Basic health check for the application will return "OK" when running
	@RequestMapping("/healthz")
	public String health() {
		return "OK";
	}

}
