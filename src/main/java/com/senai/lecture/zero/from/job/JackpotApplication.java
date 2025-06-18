package com.senai.lecture.zero.from.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class JackpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(JackpotApplication.class, args);
	}

}
