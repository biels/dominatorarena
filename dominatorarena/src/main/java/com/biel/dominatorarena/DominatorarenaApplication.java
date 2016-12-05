package com.biel.dominatorarena;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.nio.file.Files;

@SpringBootApplication
public class DominatorarenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DominatorarenaApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(){
		return args -> {
			FileSystemUtils.deleteRecursively(new File(Config.VERSION_DIR));
			System.out.println("Printing args: " + args.length);
		};
	}
}
