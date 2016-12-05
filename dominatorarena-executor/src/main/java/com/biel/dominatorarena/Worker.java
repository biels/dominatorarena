package com.biel.dominatorarena;

import com.biel.dominatorarena.api.requests.BattleResultRequest;
import com.biel.dominatorarena.api.requests.RegisterWorkerRequest;
import com.biel.dominatorarena.api.requests.WorkBlockResultRequest;
import com.biel.dominatorarena.api.responses.WorkBlockResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Biel on 1/12/2016.
 */
@Component
@EnableScheduling
public class Worker {
    @Autowired
    LocalInfo localInfo;
    @Autowired
    Registerer registerer;
    @Autowired WorkRetriever workRetriever;
    @Autowired WorkPoster workPoster;
    @Autowired EnvironmentPreparer environmentPreparer;
    @Autowired GameExecutor gameExecutor;
    WorkBlockResponse work;
    Logger l = LoggerFactory.getLogger(Worker.class);
    @Scheduled(initialDelay = 5, fixedDelay = 1000)
    void mainLoop(){
        registerer.ensureRegistered();
        work = workRetriever.getWorkWhenThereIs();
        l.info("Preparing local environment.");
        environmentPreparer.prepareLocalEnvironment();
        List<BattleResultRequest> games = gameExecutor.executeGames();
        l.info("Executed " + games.size() + " games. Posting results.");
        WorkBlockResultRequest result = new WorkBlockResultRequest(games);
        workPoster.postWork(result);
    }


}
