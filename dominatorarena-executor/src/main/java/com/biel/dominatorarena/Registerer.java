package com.biel.dominatorarena;

import com.biel.dominatorarena.api.requests.RegisterWorkerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Biel on 5/12/2016.
 */
@Component
public class Registerer {
    Logger l = LoggerFactory.getLogger(Registerer.class);
    @Autowired
    LocalInfo localInfo;
    public boolean register() {
        RestTemplate restTemplate = new RestTemplate();
        String registerEndPoint = Config.EXEC_URL;
        l.info("Trying to register...");
        HttpEntity<RegisterWorkerRequest> request = new HttpEntity<>(getRegisterWorkerRequest());
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(registerEndPoint, request, String.class);
        } catch (RestClientException e) {
            l.info("Cannot register, server unreachable.");
            return false;
        }
        boolean created = responseEntity.getStatusCode() == HttpStatus.CREATED;
        if(created)localInfo.setMyUri(responseEntity.getHeaders().getLocation());
        localInfo.setRegistered(created);
        if(created) {
            l.info("Registered successfully! as " + localInfo.getMyUri());
        }else{
            l.info("Registration failed. Status code: " + responseEntity.getStatusCode().toString());
        }
        return created;
    }
    private RegisterWorkerRequest getRegisterWorkerRequest(){
        Runtime runtime = Runtime.getRuntime();
        return new RegisterWorkerRequest(runtime.availableProcessors(), runtime.totalMemory());
    }
    public void ensureRegistered() {
        if (!localInfo.isRegistered())register();
        while (!localInfo.isRegistered()){
            l.info("Registration failed. Retrying in 5s.");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            register();
        }
    }
}
