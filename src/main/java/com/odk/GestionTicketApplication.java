package com.odk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GestionTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionTicketApplication.class, args);
	}

}
