package com.biel.dominatorarena;

import com.biel.dominatorarena.api.responses.WorkBlockResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;

/**
 * Created by Biel on 5/12/2016.
 */
@Component
public class EnvironmentPreparer { //TODO Create starting folder
    Logger l = LoggerFactory.getLogger(EnvironmentPreparer.class);
    @Autowired
    LocalInfo localInfo;
    public void prepareLocalEnvironment() {
        File folder = localInfo.getWorkingDir();
        placeStartingEnvironmentCopy();
        placeConfigs();
        placeAIs();

    }
    private void placeConfigs(){
        localInfo.getWork().getConfigurationResponses().forEach(configurationResponse -> {
            File cFile = new File("c_" + configurationResponse.getServerId() + ".txt");
            try {
                FileWriter fileWriter = new FileWriter(cFile);
                fileWriter.write(configurationResponse.getContents());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void placeAIs(){
        localInfo.getWork().getStrategyVersionResponses().forEach(strategyVersionResponse -> {
            File sFile = new File("s_" + strategyVersionResponse.getServerId() + ".cc");
            try {
                FileWriter fileWriter = new FileWriter(sFile);
                fileWriter.write(strategyVersionResponse.getCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void placeStartingEnvironmentCopy() {
        File emptyEnv = localInfo.getEmptyEnv();
        if(!emptyEnv.exists()) l.error("Starting enviroment folder not found!");
        FileSystemUtils.deleteRecursively(localInfo.getWorkingDir());
        try {
            FileSystemUtils.copyRecursively(emptyEnv, localInfo.getWorkingDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
