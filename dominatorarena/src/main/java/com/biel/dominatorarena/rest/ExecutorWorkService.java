package com.biel.dominatorarena.rest;

import com.biel.dominatorarena.api.requests.WorkBlockResultRequest;
import com.biel.dominatorarena.api.responses.*;
import com.biel.dominatorarena.model.entities.WorkBlock;
import com.biel.dominatorarena.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Biel on 30/11/2016.
 */
@RestController
@RequestMapping("/executors/{executorId}/work")
public class ExecutorWorkService {
    @Autowired
    BattleRepository battleRepository;
    @Autowired
    ConfigurationRepository configurationRepository;
    @Autowired
    StrategyVersionRepository strategyVersionRepository;
    @Autowired
    private WorkBlockRepository workBlockRepository;
    @Autowired
    private ExecutorRepository executorRepository;

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<WorkBlockResponse> getWork(@PathVariable Long executorId){
        Optional<WorkBlock> workBlockOptional = workBlockRepository.findOneByExecutor_Id(executorId);
        if(!workBlockOptional.isPresent())return new ResponseEntity<>(new WorkBlockResponse(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), HttpStatus.NO_CONTENT);
        WorkBlock workBlock = workBlockOptional.get();
        //Translate to response

        List<ConfigurationResponse> configurationResponses = configurationRepository.findByBattles_WorkBlock(workBlock).stream()
                .map(configuration -> new ConfigurationResponse(configuration.getId(), configuration.getContents()))
                .collect(Collectors.toList());
        List<StrategyVersionResponse> strategyVersionResponses = strategyVersionRepository.findByStatisticBattles_Battles_WorkBlock(workBlock).stream()
                .map(strategyVersion -> new StrategyVersionResponse(strategyVersion.getId(), strategyVersion.readSource(), strategyVersion.getStrategy().getName()))
                .collect(Collectors.toList());

        List<BattleResponse> battleResponses = workBlock.getBattles().stream()
                .map(battle -> {
                    List<PlayerResponse> playerResponses = battle.getBattlePlayers().stream()
                            .map(battlePlayer -> new PlayerResponse(battlePlayer.getStrategyVersion().getId()))
                            .collect(Collectors.toList());
                    return new BattleResponse(battle.getId(), battle.getSeed(), battle.getConfiguration().getId(), playerResponses);
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(new WorkBlockResponse(configurationResponses, strategyVersionResponses, battleResponses), HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> postWork(@PathVariable int workerId, @RequestBody WorkBlockResultRequest workBlockResultRequest){
        return ResponseEntity.ok().build();
    }

}
