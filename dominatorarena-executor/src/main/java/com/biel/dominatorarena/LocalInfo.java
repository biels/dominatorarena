package com.biel.dominatorarena;

import com.biel.dominatorarena.api.responses.WorkBlockResponse;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URI;

/**
 * Created by Biel on 5/12/2016.
 */
@Component
public class LocalInfo {
    private URI myUri;
    private boolean registered;
    WorkBlockResponse work;


    public URI getMyUri() {
        return myUri;
    }

    public void setMyUri(URI myUri) {
        this.myUri = myUri;
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
    public File getEmptyEnv(){
        return new File("emptyEnv");
    }
}
