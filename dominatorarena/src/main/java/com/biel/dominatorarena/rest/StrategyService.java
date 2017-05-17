package com.biel.dominatorarena.rest;


import com.biel.dominatorarena.logic.AutoStrategyVersionImporter;
import com.biel.dominatorarena.logic.StatisticBattleGenerator;
import com.biel.dominatorarena.model.entities.StrategyVersion;
import com.biel.dominatorarena.model.repositories.StrategyVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Biel on 30/11/2016.
 */
@RestController
@RequestMapping("/service/strategy")
public class StrategyService {
    @Autowired
    AutoStrategyVersionImporter autoStrategyVersionImporter;
    @Autowired
    StatisticBattleGenerator statisticBattleGenerator;
    @Autowired
    StrategyVersionRepository strategyVersionRepository;
    Logger l = LoggerFactory.getLogger(StrategyService.class);
    @RequestMapping(path = "/import", method = RequestMethod.GET)
    public ResponseEntity<?> importStrategies(@RequestParam(defaultValue = "false") boolean autoDummy){
        l.info("Importing...");
        List<StrategyVersion> strategyVersions = autoStrategyVersionImporter.autoImport();
        if(autoDummy){
            Optional<StrategyVersion> dummy = strategyVersionRepository.findOneByStrategy_Name("Dummy");
            if(dummy.isPresent()){
                strategyVersions.stream().filter(sv -> !sv.getStrategy().getName().equals("Dummy"))
                        .forEach(sv -> statisticBattleGenerator.generateStatisticBattleFromVersions(
                                Stream.of(sv, dummy.get()).collect(Collectors.toList()), true));
            }
        }
        return new ResponseEntity<List<StrategyVersion>>(strategyVersions, HttpStatus.OK);
    }
    @RequestMapping(path = "/export", method = RequestMethod.GET)
    public ResponseEntity<?> exportStrategy(@RequestParam String path, @RequestParam Long svId){
        StrategyVersion strategyVersion = strategyVersionRepository.findOne(svId);
        if (strategyVersion == null)return ResponseEntity.badRequest().body("Strategy version " + svId + " does not exist");
        if (strategyVersion.isCompiled())return ResponseEntity.badRequest().body("Strategy version is compiled");
        File destFile = new File(path);
        //if(!destFile.exists())return ResponseEntity.badRequest().body("Path " + path + " does not exist.");
        //try {
            //destFile.delete();

        //FileChannel.open(destFile.toPath(), StandardOpenOption.TRUNCATE_EXISTING).truncate(0).close();
        //}
//        catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body("Error truncating file.");
//        }
        try {
            BufferedWriter bufferedWriter = Files.newBufferedWriter(destFile.toPath(), StandardOpenOption.TRUNCATE_EXISTING);
            bufferedWriter.write(strategyVersion.readSource());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<StrategyVersion>(strategyVersion, HttpStatus.OK);
    }
}
