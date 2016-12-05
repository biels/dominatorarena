package com.biel.dominatorarena;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.FileSystemUtils;

import java.io.File;

@SpringBootApplication
public class DominatorarenaExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DominatorarenaExecutorApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(){
		return args -> {

		};
	}
}
