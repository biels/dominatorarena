package com.biel.dominatorarena;

import com.biel.dominatorarena.logic.AutoStrategyVersionImporter;
import com.biel.dominatorarena.logic.StatisticBattleGenerator;
import com.biel.dominatorarena.model.entities.Configuration;
import com.biel.dominatorarena.model.entities.StrategyVersion;
import com.biel.dominatorarena.model.repositories.ConfigurationRepository;
import com.biel.dominatorarena.model.repositories.ExecutorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class DominatorarenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DominatorarenaApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(ConfigurationRepository configurationRepository,
							 ExecutorRepository executorRepository,
							 AutoStrategyVersionImporter autoStrategyVersionImporter,
							 StatisticBattleGenerator statisticBattleGenerator){
		return args -> {
			FileSystemUtils.deleteRecursively(new File(Config.VERSION_DIR));
			System.out.println("Printing args: " + args.length);
            //Clear executors
            executorRepository.deleteAll();

            //


			//Populate sample configs
			//TODO
			Configuration c1 = new Configuration("Cube Default");
			c1.setMapName("cube");
			configurationRepository.save(c1);
			Configuration c2 = new Configuration("Plane Default");
			c2.setMapName("plane");
			configurationRepository.save(c2);
			Configuration c3 = new Configuration("Icosahedron default");
			c3.setMapName("icosahedron");
			configurationRepository.save(c3);

			List<StrategyVersion> strategyVersions = autoStrategyVersionImporter.autoImport();

			statisticBattleGenerator.generateStatisticBattleFromVersions(strategyVersions.stream().limit(4).collect(Collectors.toList()));
		};
	}
}
