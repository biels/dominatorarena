package com.biel.dominatorarena.logic;

import com.biel.dominatorarena.Config;
import com.biel.dominatorarena.logic.types.SourceAnnotation;
import com.biel.dominatorarena.model.entities.Strategy;
import com.biel.dominatorarena.model.entities.StrategyVersion;
import com.biel.dominatorarena.model.repositories.StrategyRepository;
import com.biel.dominatorarena.model.repositories.StrategyVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Biel on 1/12/2016.
 */
@Component
public class AutoStrategyVersionImporter {

    Logger l = LoggerFactory.getLogger(AutoStrategyVersionImporter.class);
    @Autowired
    StrategyVersionRepository strategyVersionRepository;
    @Autowired
    StrategyRepository strategyRepository;

    public List<StrategyVersion> autoImport(){
        File directory = new File(Config.INPUT_DIR);
        try {
            Files.createDirectories(directory.toPath());
            return importNewVersionsAutomatically(directory);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    List<StrategyVersion> importNewVersionsAutomatically(File directory) throws NoSuchAlgorithmException, IOException {
        List<StrategyVersion> imported = new ArrayList<>();
        if (!directory.isDirectory()) throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        for (File file : directory.listFiles()) {
            String fileName = file.getName();
            String[] split = fileName.split("\\.");
            //Check extension
            if(split.length < 1 || !(Objects.equals(split[1], "cpp") || Objects.equals(split[1], "cc")))continue;
            //Check if is new version
            byte[] buffer = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(buffer);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(buffer);
            Optional<StrategyVersion> strategyVersion = strategyVersionRepository.findOneByDigest(digest);
            boolean newVersion = !strategyVersion.isPresent();
            if (newVersion) {
                try {
                    imported.add(importVersionFromFile(file, digest));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                l.info("Imported new version from " + file.getName());
            }
        }
        return imported;
    }


    StrategyVersion importVersionFromFile(File file, byte[] digest) throws Exception {
        List<SourceAnnotation> sourceAnnotations = readAnnotations(file);
        SourceAnnotation strategyAnnotation = sourceAnnotations.stream().filter(a -> a.isOfType("Strategy"))
                .findFirst().orElseThrow(() -> new Exception("Strategy annotation not found"));
        String strategyName = strategyAnnotation.getArgs().get(0);
        Strategy strategy;
        Optional<Strategy> strategyOptional = strategyRepository.findOneByName(strategyName);
        if(!strategyOptional.isPresent()){
            strategy = strategyRepository.save(new Strategy(strategyName));
        }else {
            strategy = strategyOptional.get();
        }
        StrategyVersion strategyVersion = strategyVersionRepository.save(new StrategyVersion(strategy, digest));
        File input = strategyVersion.getSourceFile();
        Files.createDirectories(input.toPath().getParent());
        Files.copy(file.toPath(), input.toPath());
        return  strategyVersion;
    }


    private List<SourceAnnotation> readAnnotations(File file) throws IOException {
        return Files.lines(file.toPath()).filter(s -> s.startsWith("//@")).map(s -> s.substring(3)).map(s -> {
            String[] split = s.split(" ");
            List<String> strings = Arrays.asList(Arrays.copyOfRange(split, 1, split.length));
            return new SourceAnnotation(split[0], strings);
        }).collect(Collectors.toList());
    }
}
