package com.biel.dominatorarena.rest;

import com.biel.dominatorarena.api.requests.BattlePlayerResultRequest;
import com.biel.dominatorarena.api.requests.BattleResultRequest;
import com.biel.dominatorarena.api.requests.WorkBlockResultRequest;
import com.biel.dominatorarena.api.responses.*;
import com.biel.dominatorarena.logic.AutoStrategyVersionImporter;
import com.biel.dominatorarena.logic.StatisticBattleReportGenerator;
import com.biel.dominatorarena.logic.WorkAssigner;
import com.biel.dominatorarena.model.entities.*;
import com.biel.dominatorarena.model.repositories.*;
import org.jooq.lambda.Seq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Biel on 30/11/2016.
 */
@RestController
@RequestMapping("/executors/{executorId}/work")
public class ExecutorWorkService {
    @Autowired
    BattleRepository battleRepository;
    @Autowired
    BattlePlayerRepository battlePlayerRepository;
    @Autowired
    BattlePlayerResultRepository battlePlayerResultRepository;
    @Autowired
    BattleResultRepository battleResultRepository;
    @Autowired
    ConfigurationRepository configurationRepository;
    @Autowired
    StrategyVersionRepository strategyVersionRepository;
    @Autowired
    private WorkBlockRepository workBlockRepository;
    @Autowired
    private ExecutorRepository executorRepository;
    @Autowired
    WorkAssigner workAssigner;
    @Autowired
    StatisticBattleReportGenerator statisticBattleReportGenerator;
    Logger l = LoggerFactory.getLogger(ExecutorWorkService.class);

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<WorkBlockResponse> getWork(@PathVariable Long executorId) {
        Executor executor = executorRepository.findOne(executorId);
        if (executor == null) return ResponseEntity.badRequest().build();
        Optional<WorkBlock> workBlockOptional = workBlockRepository.findOneByExecutor_IdAndExecutorIsNull(executorId);
        if (!workBlockOptional.isPresent()) {
            workBlockOptional = workAssigner.assignWorkToExecutor(executor);
        }
        //If still not present, tell that there is no work to do
        if (!workBlockOptional.isPresent())
            return new ResponseEntity<>(new WorkBlockResponse(-1L, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), HttpStatus.NO_CONTENT);

        WorkBlock workBlock = workBlockOptional.get();
        //Translate to response
        List<ConfigurationResponse> configurationResponses = workBlock.getBattles().stream()
                .map(b -> b.getConfiguration())
                .distinct()
                .map(c -> new ConfigurationResponse(c.getId(), c.getName(), c.toConfigFileContent()))
                .collect(Collectors.toList());
//                configurationRepository.findByBattles_WorkBlock(workBlock).stream()
//                .map(configuration -> new ConfigurationResponse(configuration.getId(), configuration.getName(), configuration.toConfigFileContent()))
//                .collect(Collectors.toList());
        List<StrategyVersionResponse> strategyVersionResponses = workBlock.getBattles().stream()
                .flatMap(battle -> battle.getBattlePlayers().stream())
                .map(battlePlayer -> battlePlayer.getStrategyVersion())
                .distinct()
                .map(strategyVersion -> new StrategyVersionResponse(strategyVersion.getId(), strategyVersion.readSource(), strategyVersion.getStrategy().getName()))
                .collect(Collectors.toList());
//                strategyVersionRepository.findByStatisticBattles_Battles_WorkBlock(workBlock).stream()
//                .map(strategyVersion -> new StrategyVersionResponse(strategyVersion.getId(), strategyVersion.readSource(), strategyVersion.getStrategy().getName()))
//                .collect(Collectors.toList());

        List<BattleResponse> battleResponses = workBlock.getBattles().stream()
                .map(battle -> {
                    List<PlayerResponse> playerResponses = battle.getBattlePlayers().stream()
                            .map(battlePlayer -> new PlayerResponse(battlePlayer.getStrategyVersion().getId()))
                            .collect(Collectors.toList());
                    return new BattleResponse(battle.getId(), battle.getSeed(), battle.getConfiguration().getId(), playerResponses);
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(new WorkBlockResponse(workBlock.getId(), configurationResponses, strategyVersionResponses, battleResponses), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> postWork(@PathVariable int executorId, @RequestBody WorkBlockResultRequest workBlockResultRequest) {
        l.info("Work block #" + workBlockResultRequest.getWorkBlockId() + " received");
        WorkBlock workBlock = workBlockRepository.findOne(workBlockResultRequest.getWorkBlockId());
        if (workBlock == null) return ResponseEntity.notFound().build();
        if (workBlock.isExecuted()) return ResponseEntity.badRequest().body("Block already marked as executed");
        workBlock.setExecuted(true);

        List<BattleResultRequest> battleResultRequests = workBlockResultRequest.getBattleResultRequests();
        battleResultRequests.forEach(battleResultRequest -> {
            Battle battle = battleRepository.findOne(battleResultRequest.getBattleId());
            List<BattlePlayerResultRequest> battlePlayerResultRequests = battleResultRequest.getBattlePlayerResultRequests();
            int max = 0, maxi = 0;
            for (int i = 0; i < battlePlayerResultRequests.size(); i++) {
                BattlePlayer battlePlayer = battle.getBattlePlayers().get(i);
                BattlePlayerResultRequest battlePlayerResultRequest = battlePlayerResultRequests.get(i);
                int score = battlePlayerResultRequest.getScore();
                if (score > max) {
                    max = score;
                    maxi = i;
                }
                BattlePlayerResult result = new BattlePlayerResult(battlePlayer);
                result.setScore(score);
                battlePlayer.setResult(battlePlayerResultRepository.save(result));
            }
            List<BattlePlayerResult> battlePlayerResults = battle.getBattlePlayers().stream()
                    .map(bp -> bp.getResult())
                    .sorted((a, b) -> Long.compare(b.getScore(), a.getScore())).collect(Collectors.toList());
            IntStream.range(1, battlePlayerResults.size() + 1)
                    .forEach(i -> battlePlayerResults.get(i-1).setPlace(i));

            BattleResult battleResult = new BattleResult(battle.getBattlePlayers().get(maxi));
            battleResult.setBattle(battle);
            battle.setResult(battleResultRepository.save(battleResult));
            battleRepository.save(battle);
        });
        workBlockRepository.save(workBlock);
        workBlock.getBattles().stream()
                .map(b -> b.getStatisticBattle()).distinct()
                .forEach(sb -> statisticBattleReportGenerator.generateReport(sb));
        l.info("Report for block #" + workBlockResultRequest.getWorkBlockId() + " generated");
        return ResponseEntity.ok().build();
    }

}
