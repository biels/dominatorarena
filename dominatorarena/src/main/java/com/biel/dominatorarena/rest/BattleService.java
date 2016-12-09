package com.biel.dominatorarena.rest;

import com.biel.dominatorarena.api.requests.BattleCreationRequest;
import com.biel.dominatorarena.logic.StatisticBattleGenerator;
import com.biel.dominatorarena.model.entities.Battle;
import com.biel.dominatorarena.model.entities.StatisticBattle;
import com.biel.dominatorarena.model.entities.StrategyVersion;
import com.biel.dominatorarena.model.repositories.BattlePlayerRepository;
import com.biel.dominatorarena.model.repositories.BattleRepository;
import com.biel.dominatorarena.model.repositories.StrategyRepository;
import com.biel.dominatorarena.model.repositories.StrategyVersionRepository;
import com.sun.deploy.net.HttpResponse;
import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * Created by Biel on 9/12/2016.
 */
@RestController
@RequestMapping("/battle")
public class BattleService {
    @Autowired
    StatisticBattleGenerator statisticBattleGenerator;
    @Autowired
    StrategyRepository strategyRepository;
    @Autowired
    StrategyVersionRepository strategyVersionRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<StatisticBattle> create(@RequestBody BattleCreationRequest battleCreationRequest){
        StatisticBattle result = null;
        if(battleCreationRequest.isUsingStrategyId()){
            statisticBattleGenerator.generateStatisticBattleFromStrategies(
                    battleCreationRequest.getIds().stream()
                            .map(id -> strategyRepository.findOne(id))
                            .collect(Collectors.toList())
            );
        }else{
            statisticBattleGenerator.generateStatisticBattleFromVersions(
                    battleCreationRequest.getIds().stream()
                            .map(versionId -> strategyVersionRepository.findOne(versionId))
                            .collect(Collectors.toList())
            );
        }
        return ResponseEntity.ok(result);
    }
}
