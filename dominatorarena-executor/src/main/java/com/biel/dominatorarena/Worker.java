package com.biel.dominatorarena;

import com.biel.dominatorarena.api.requests.BattleResultRequest;
import com.biel.dominatorarena.api.requests.WorkBlockResultRequest;
import com.biel.dominatorarena.api.responses.WorkBlockResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by Biel on 1/12/2016.
 */
@Component
@EnableScheduling
public class Worker {

    @Autowired
    private ApplicationContext appContext;
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
        work = workRetriever.getWork();
        if(work == null)return;
        l.info("Processing block " + work.getWorkBlockId() + "...");
        l.info("Preparing local environment.");
        if(!environmentPreparer.prepareLocalEnvironment()){
            l.error("Could not prepare local envionment, closing application.");
            SpringApplication.exit(appContext, () -> 1);
            return;
        }
        List<BattleResultRequest> games = gameExecutor.executeGames();
        l.info("Executed " + games.size() + " games. Posting results.");
        WorkBlockResultRequest result = new WorkBlockResultRequest(games);
        workPoster.postWork(result);

    }


}
