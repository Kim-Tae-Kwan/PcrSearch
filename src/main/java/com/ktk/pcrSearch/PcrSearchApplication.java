package com.ktk.pcrSearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PcrSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcrSearchApplication.class, args);
	}

}
