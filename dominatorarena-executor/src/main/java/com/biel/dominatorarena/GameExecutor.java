package com.biel.dominatorarena;

import com.biel.dominatorarena.api.requests.BattlePlayerResultRequest;
import com.biel.dominatorarena.api.requests.BattleResultRequest;
import com.biel.dominatorarena.api.responses.BattleResponse;
import com.biel.dominatorarena.api.responses.PlayerResponse;
import com.biel.dominatorarena.api.responses.StrategyVersionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
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
        return localInfo.getWork().getBattleResponses().stream() //Could be parallelized with appropriate filenames
                .map(battleResponse -> executeBattleBlocking(battleResponse))
                .filter(battleResultRequest -> battleResultRequest != null)
                .collect(Collectors.toList());
    }
    private BattleResultRequest executeBattleBlocking(BattleResponse battle){
        List<PlayerResponse> players = battle.getPlayerResponses();
        String config = "c_" + battle.getConfigurationId() + ".cnf";
        List<String> playerNames = battle.getPlayerResponses().stream()
                .sorted(Comparator.comparingInt(PlayerResponse::getSlot))
                .map(playerResponse -> localInfo.getWork().getStrategyVersionResponses().stream()
                    .filter(strategyVersionResponse -> strategyVersionResponse.getServerId() == playerResponse.getStrategyId())
                    .findFirst().get())
                .map(StrategyVersionResponse::getName)
                .collect(Collectors.toList());
        List<String> gameArgs = new ArrayList<>();
        //./Game Demo Demo Demo Demo -s 30 -i default.cnf -o default.res
        gameArgs.add("./Game");
        gameArgs.addAll(playerNames);
        gameArgs.add("-s " + battle.getSeed());
        //args.add("-i " + config);
        String outName = "default" + random.nextInt(1000);
        String fullOutName = outName + ".t3d";
        gameArgs.add("-o " + fullOutName);
        String fullGameCommand = String.join(" ", gameArgs);
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(new File(localInfo.getArenaDir().getPath() + "/script.sh")));
            fileWriter.write(fullGameCommand);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProcessBuilder pbChmod = new ProcessBuilder("chmod", "a+x", "script.sh").directory(localInfo.getArenaDir());
        ProcessBuilder pb = new ProcessBuilder()
                .directory(localInfo.getArenaDir())
                .command("./script.sh").redirectError(ProcessBuilder.Redirect.INHERIT)
                .redirectInput(new File(localInfo.getArenaDir().getPath() + "/" + config));
                //.inheritIO();
        try {
            l.info("Setting executable bit...");
            pbChmod.start().waitFor();
            l.info("Executing " + fullGameCommand);
            Process gameProcess = pb.start();
            gameProcess.waitFor();
            l.info("Execution finished (" + fullGameCommand + ").");
        } catch (IOException e) {
            l.error("Could not execute game executable");
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            l.error("Game executable was interrupted");
            return null;
        }
        //String gameResult = shellCommandExecutor.executeCommandBlocking("./Game", args.toArray(new String[0]), localInfo.getArenaDir());
        //TODO Read command result and / or generated file to fill result
        try {
            Scanner scanner = new Scanner(new File(localInfo.getArenaDir().getPath() + "/stats.txt"));
            ArrayList<BattlePlayerResultRequest> battlePlayerResultRequests = new ArrayList<>();
            int slot = 0;
            while (scanner.hasNext()){
                int score = scanner.nextInt();
                BattlePlayerResultRequest battlePlayerResultRequest = new BattlePlayerResultRequest(score, 0);
                battlePlayerResultRequest.setSlot(slot++);
                battlePlayerResultRequests.add(battlePlayerResultRequest);
            }
            return new BattleResultRequest(battle.getBattleId(), battlePlayerResultRequests);
        } catch (FileNotFoundException e) {
            l.error("Stats file not found for battle " + battle.getBattleId() + ".");
            return null;
        }
    }
}
