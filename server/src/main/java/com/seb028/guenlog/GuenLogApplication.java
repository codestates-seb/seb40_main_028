package com.seb028.guenlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GuenLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuenLogApplication.class, args);
	}

}
