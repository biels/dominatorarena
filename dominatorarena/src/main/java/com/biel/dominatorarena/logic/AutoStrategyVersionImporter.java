package com.biel.dominatorarena.logic;

import com.biel.dominatorarena.Config;
import com.biel.dominatorarena.logic.types.SourceAnnotation;
import com.biel.dominatorarena.model.entities.Strategy;
import com.biel.dominatorarena.model.entities.StrategyVersion;
import com.biel.dominatorarena.model.entities.StrategyVersionFeature;
import com.biel.dominatorarena.model.entities.StrategyVersionFeatureParam;
import com.biel.dominatorarena.model.repositories.StrategyRepository;
import com.biel.dominatorarena.model.repositories.StrategyVersionFeatureParamRepository;
import com.biel.dominatorarena.model.repositories.StrategyVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
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
    @Autowired
    StrategyVersionFeatureParamRepository strategyVersionFeatureParamRepository;
    List<File> additionalImportDirs = new ArrayList<>();

    public List<StrategyVersion> autoImport() {
        File directory = new File(Config.INPUT_DIR);
        try {
            Files.createDirectories(directory.toPath());
            List<StrategyVersion> imported = importNewVersionsAutomatically(directory, true);
            readAdditionalImportDirs();
            additionalImportDirs.forEach(f -> {
                try {
                    imported.addAll(importNewVersionsAutomatically(f, false));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return imported;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<StrategyVersion> importNewVersionsAutomatically(File directory, boolean acceptBinaryFiles) throws NoSuchAlgorithmException, IOException {
        List<StrategyVersion> imported = new ArrayList<>();
        if (!directory.isDirectory())
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        for (File file : directory.listFiles()) {
            String fileName = file.getName();
            String[] split = fileName.split("\\.");
            //Check extension
            if (split[0].length() < 2) continue;
            if (!split[0].startsWith("AI")) continue;
            boolean compiled = false;
            boolean accepted = false;
            if (split.length > 1) {
                String ext = split[1];
                if ((Objects.equals(ext, "cpp") || Objects.equals(ext, "cc"))) {
                    compiled = false;
                    accepted = true;
                }
                if ((Objects.equals(ext, "o") || Objects.equals(ext, "obj"))) {
                    compiled = true;
                    accepted = acceptBinaryFiles;
                }

            } else {
                continue;
            }
            if (!accepted) continue;
            //Check if is new version
            byte[] buffer = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(buffer);
            fileInputStream.close();
            fileInputStream = null;
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(buffer);
            Optional<StrategyVersion> strategyVersion = strategyVersionRepository.findOneByDigest(digest);
            boolean newVersion = !strategyVersion.isPresent();
            if (newVersion) {
                try {
                    String filename = split[0];
                    imported.add(importVersionFromFile(file, digest, filename, compiled));
                } catch (Exception e) {
                    l.error("Error importing new version from " + file.getName(), e);
                    e.printStackTrace();
                }
                l.info("Imported new version from " + file.getName());
            }
        }
        return imported;
    }


    StrategyVersion importVersionFromFile(File file, byte[] digest, String filename, boolean compiled) throws Exception {
//        List<SourceAnnotation> sourceAnnotations = readAnnotations(file);
//        SourceAnnotation strategyAnnotation = sourceAnnotations.stream().filter(a -> a.isOfType("Strategy"))
//                .findFirst().orElseThrow(() -> new Exception("Strategy annotation not found"));
        String strategyName = compiled ?
                getStrategyNameFromFileName(filename) :
                getStrategyNameFromContents(file);
        Strategy strategy;
        Optional<Strategy> strategyOptional = strategyRepository.findOneByName(strategyName);
        if (!strategyOptional.isPresent()) {
            strategy = strategyRepository.save(new Strategy(strategyName));
        } else {
            strategy = strategyOptional.get();
        }
        StrategyVersion strategyVersion = strategyVersionRepository.save(new StrategyVersion(strategy, digest));
        strategyVersion.setCompiled(compiled);

        if(!compiled){
            List<SourceAnnotation> annotations = readAnnotations(file);
            List<StrategyVersionFeature> features = annotations.stream()
                    .filter(a -> (a.isOfType("Feature") || a.isOfType("F")) && a.getArgs().size() >= 1)
                    .map(a -> {
                        StrategyVersionFeature feature = new StrategyVersionFeature(strategyVersion, a.getArgs().get(0));
                        if(a.getArgs().size() >= 2)feature.setAlias(a.getArgs().get(1));
                        return feature;
                    })
                    .collect(Collectors.toList());
            strategyVersion.setFeatures(features);
            strategyVersionRepository.save(strategyVersion);

            List<StrategyVersionFeatureParam> featureParams = annotations.stream()
                    .filter(a -> (a.isOfType("Param") || a.isOfType("P")) && a.getArgs().size() >= 3)
                    .map(a -> {
                        String featureNameOrAlias = a.getArgs().get(0);
                        String paramName = a.getArgs().get(1);
                        String paramValue = a.getArgs().get(2);
                        Optional<StrategyVersionFeature> featureOptional = strategyVersion.getFeatures().stream()
                                .filter(f -> f.getName().equalsIgnoreCase(featureNameOrAlias)
                                        || (f.getAlias() != null && f.getAlias().equalsIgnoreCase(featureNameOrAlias)))
                                .findAny();
                        if (!featureOptional.isPresent()) return null;
                        return new StrategyVersionFeatureParam(featureOptional.get(), paramName, paramValue);
                    }).filter(p -> p != null)
                    .collect(Collectors.toList());
            strategyVersionFeatureParamRepository.save(featureParams);
        }

        File sourceFile = strategyVersion.getSourceFile();
        Files.createDirectories(sourceFile.toPath().getParent());
        Files.copy(file.toPath(), sourceFile.toPath());
        // Load params
        return strategyVersionRepository.findOne(strategyVersionRepository.save(strategyVersion).getId());
    }

    private String getStrategyNameFromFileName(String filename) {
        String prefix = "AI";
        String result = filename;
        if (filename.startsWith(prefix)) {
            result = filename.substring(prefix.length());
        }
        return result;
    }

    private String getStrategyNameFromContents(File file) throws Exception {
        String prefix = "#define PLAYER_NAME";
        return Files.lines(file.toPath())
                .filter(s -> s.startsWith(prefix))
                .findFirst().orElseThrow(() -> new Exception("Could not detect player name definition"))
                .trim()
                .substring(prefix.length())
                .trim();
    }


    private List<SourceAnnotation> readAnnotations(File file) throws IOException {
        return Files.lines(file.toPath())
                .map(String::trim)
                .filter(s -> s.startsWith("//@"))
                .map(s -> s.substring(3)).map(s -> {
                    String[] split = s.split(" ");
                    List<String> strings = Arrays.asList(Arrays.copyOfRange(split, 1, split.length));
                    return new SourceAnnotation(split[0], strings);
                }).collect(Collectors.toList());
    }

    private void readAdditionalImportDirs() throws IOException {
        additionalImportDirs.clear();
        File file = new File(Config.FILES + "/" + "additionalImportDirs.txt");
        if (!file.exists()) file.createNewFile();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            additionalImportDirs.add(new File(scanner.nextLine()));
        }
    }
}
