package com.biel.dominatorarena;

import com.biel.dominatorarena.api.responses.WorkBlockResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Created by Biel on 5/12/2016.
 */
@Component
public class WorkRetriever {
    Logger l = LoggerFactory.getLogger(WorkRetriever.class);
    @Autowired
    LocalInfo localInfo;
    private ResponseEntity<WorkBlockResponse> getWorkBlockResponse() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = localInfo.getMyUri() + "/work";
            return restTemplate.getForEntity(url, WorkBlockResponse.class);
        } catch (RestClientException e) {
            //e.printStackTrace();
        }
        return null;
    }
    public WorkBlockResponse getWork(){
        ResponseEntity<WorkBlockResponse> workBlockResponse;
        while (true){
            workBlockResponse = getWorkBlockResponse();
            boolean available = true;
            if(available && workBlockResponse == null){
                l.info("Server unreachable. No longer registered");
                localInfo.setRegistered(false);
                available = false;
                return null;
            }
            if(available && workBlockResponse.getStatusCode() != HttpStatus.OK){
                //If code is not 200, it means that there is no work to do
                l.info("No work to do.");
                available = false;
            }
            if(available)break;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return workBlockResponse.getBody();
    }
}
