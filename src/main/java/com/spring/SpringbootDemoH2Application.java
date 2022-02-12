package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootDemoH2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemoH2Application.class, args);
	}

}
