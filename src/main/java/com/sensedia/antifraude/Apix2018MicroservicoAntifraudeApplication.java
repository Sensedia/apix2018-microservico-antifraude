package com.sensedia.antifraude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.sensedia.antifraude")
@EnableAutoConfiguration
public class Apix2018MicroservicoAntifraudeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Apix2018MicroservicoAntifraudeApplication.class, args);
	}
}
