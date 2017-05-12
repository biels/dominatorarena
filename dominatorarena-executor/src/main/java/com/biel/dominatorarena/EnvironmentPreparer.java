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
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;

/**
 * Created by Biel on 5/12/2016.
 */
@Component
public class EnvironmentPreparer { //TODO Create starting folder
    Logger l = LoggerFactory.getLogger(EnvironmentPreparer.class);
    @Autowired
    ShellCommandExecutor shellCommandExecutor;
    @Autowired
    LocalInfo localInfo;

    public boolean prepareLocalEnvironment() {
        File folder = localInfo.getWorkingDir();
        if (folder.mkdir()) {
            l.info("Fill copyFrom.txt");
            try {
                new File(folder.getPath() + "/copyFrom.txt").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (placeStartingEnvironmentCopy()) {
            placeConfigs();
            placeAIs();
            if (!compileEnvironment()) return false;
            return true;
        }
        return false;
    }

    private void placeConfigs() {
        localInfo.getWork().getConfigurationResponses().forEach(configurationResponse -> {
            File cFile = new File(localInfo.getArenaDir() + "/" + "c_" + configurationResponse.getServerId() + ".cnf");
            try {
                FileWriter fileWriter = new FileWriter(cFile);
                fileWriter.write(configurationResponse.getContents());
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void placeAIs() {
        localInfo.getWork().getStrategyVersionResponses().forEach(strategyVersionResponse -> {
            boolean binary = false;
            String ext = ".cc";
            if (strategyVersionResponse.getCompiled() != null) {
                ext = ".o";
                binary = true;
            }
            String pathname = localInfo.getArenaDir() + "/" + "AIs_" + strategyVersionResponse.getServerId() + ext;
            File sFile = new File(pathname);

            try {
                if(binary){
                    Files.write(sFile.toPath(), strategyVersionResponse.getCompiled(), StandardOpenOption.CREATE);
                }else {
                    FileWriter fileWriter = new FileWriter(sFile);
                    fileWriter.write(strategyVersionResponse.getCode());
                    fileWriter.flush();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean placeStartingEnvironmentCopy() {
        File emptyEnv = localInfo.getEmptyEnv();
        if (!emptyEnv.exists()) {
            l.info(new File("").getAbsolutePath());
            l.error("Starting enviroment folder not found!");
            return false;
        }
        FileSystemUtils.deleteRecursively(localInfo.getArenaDir());
        try {
            FileSystemUtils.copyRecursively(emptyEnv, localInfo.getArenaDir());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean compileEnvironment() {
        //String output = shellCommandExecutor.executeCommandBlocking("sh ./compile.sh", new String[]{}, localInfo.getWorkingDir());
        //if(output.endsWith("failed")) return false;
        Process make;
        try {
            ProcessBuilder pb = new ProcessBuilder("./compile.sh");
            pb.inheritIO();
            pb.directory(localInfo.getWorkingDir());
            File game = new File(localInfo.getArenaDir().getPath() + "/Game");
            if (game.exists())
                game.delete();
            make = pb.start();
            l.info("Compiling arena...");
            make.waitFor();
            if (game.exists()) {
                l.info("Compilation finished successfully.");
                return true;
            } else {
                l.info("Compilation failed.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
