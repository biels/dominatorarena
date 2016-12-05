package com.biel.dominatorarena;

import com.biel.dominatorarena.api.requests.BattleResultRequest;
import com.biel.dominatorarena.api.responses.BattleResponse;
import com.biel.dominatorarena.api.responses.PlayerResponse;
import com.biel.dominatorarena.api.responses.StrategyVersionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Biel on 5/12/2016.
 */
@Component
public class GameExecutor {
    Logger l = LoggerFactory.getLogger(GameExecutor.class);
    Random random = new Random();
    @Autowired LocalInfo localInfo;
    @Autowired ShellCommandExecutor shellCommandExecutor;
    public List<BattleResultRequest> executeGames(){
        return localInfo.getWork().getBattleResponses().parallelStream()
                .map(battleResponse -> executeBattleBlocking(battleResponse)).collect(Collectors.toList());
    }
    private BattleResultRequest executeBattleBlocking(BattleResponse battle){
        List<PlayerResponse> players = battle.getPlayerResponses();
        String config = "c_" + battle.getConfigurationId() + ".txt";
        List<String> playerNames = battle.getPlayerResponses().stream()
                .map(playerResponse -> localInfo.getWork().getStrategyVersionResponses().stream()
                    .filter(strategyVersionResponse -> strategyVersionResponse.getServerId() == playerResponse.getStrategyId())
                    .findFirst().get())
                .map(StrategyVersionResponse::getName)
                .collect(Collectors.toList());
        List<String> args = new ArrayList<>();
        //./Game Demo Demo Demo Demo -s 30 -i default.cnf -o default.res
        args.addAll(playerNames);
        args.add("-s " + battle.getSeed());
        args.add("-i " + config);
        String outName = "default" + random.nextInt(1000);
        args.add("-o " + outName + ".res");
        l.info("Executing ./Game " + String.join(" ", args));
        shellCommandExecutor.executeCommandBlocking("./Game", args.toArray(new String[0]), localInfo.getWorkingDir());
        //TODO Read command result and / or generated file to fill result
        return new BattleResultRequest();
    }
}
