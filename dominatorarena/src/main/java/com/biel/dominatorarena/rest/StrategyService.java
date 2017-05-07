package com.biel.dominatorarena.rest;


import com.biel.dominatorarena.logic.AutoStrategyVersionImporter;
import com.biel.dominatorarena.model.entities.StrategyVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Biel on 30/11/2016.
 */
@RestController
@RequestMapping("/service/strategy")
public class StrategyService {
    @Autowired
    AutoStrategyVersionImporter autoStrategyVersionImporter;
    Logger l = LoggerFactory.getLogger(StrategyService.class);
    @RequestMapping(path = "/import", method = RequestMethod.GET)
    public ResponseEntity<?> importStrategies(){
        l.info("Importing...");
        return new ResponseEntity<List<StrategyVersion>>(autoStrategyVersionImporter.autoImport(), HttpStatus.OK);
    }
}
