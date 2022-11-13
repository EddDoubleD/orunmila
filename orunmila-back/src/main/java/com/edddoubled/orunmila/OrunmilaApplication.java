package com.edddoubled.orunmila;

import com.edddoubled.orunmila.service.InitializationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@SpringBootApplication
public class OrunmilaApplication implements CommandLineRunner {

	InitializationService initializationService;
	Environment env;


	public static void main(String[] args) {
		SpringApplication.run(OrunmilaApplication.class, args);
	}

	@Override
	public void run(String... args) {

		initializationService.init(Boolean.parseBoolean(env.getProperty("initDb")));
	}
}
