package com.biel.dominatorarena;

import com.biel.dominatorarena.api.responses.WorkBlockResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;

/**
 * Created by Biel on 5/12/2016.
 */
@Component
public class LocalInfo {
    private URI myUri;
    private boolean registered;
    WorkBlockResponse work;

    Logger l = LoggerFactory.getLogger(LocalInfo.class);
    public URI getMyUri() {
        return myUri;
    }

    public void setMyUri(URI myUri) {
        this.myUri = myUri;
    }

    public int getMyId(){
        return Integer.parseInt(getLastBitFromUrl(getMyUri().toString()));
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public WorkBlockResponse getWork() {
        return work;
    }

    public void setWork(WorkBlockResponse work) {
        this.work = work;
    }
    public File getWorkingDir(){
        return new File("working");
    }
    public File getArenaDir(){
        return new File(getWorkingDir().getPath() + "/arena");
    }
    public File getEmptyEnv(){
        File pathFile = new File(getWorkingDir().getPath() + "/copyFrom.txt");
        String path;
        try {
            Scanner scanner = new Scanner(pathFile);
            path = scanner.nextLine();
        } catch (FileNotFoundException e) {
            l.info("Fill " + pathFile.getPath());
            return null;
        }
        return new File(path);
    }


    public static String getLastBitFromUrl(final String url){
        // return url.replaceFirst("[^?]*/(.*?)(?:\\?.*)","$1);" <-- incorrect
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }
}
