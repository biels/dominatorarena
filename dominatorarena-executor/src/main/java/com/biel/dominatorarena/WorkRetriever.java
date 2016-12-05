package com.biel.dominatorarena;

import com.biel.dominatorarena.api.responses.WorkBlockResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
        return restTemplate.getForEntity(localInfo.getMyUri() + "/work", WorkBlockResponse.class);
    }
    public WorkBlockResponse getWorkWhenThereIs(){
        ResponseEntity<WorkBlockResponse> workBlockResponse = getWorkBlockResponse();
        while (workBlockResponse.getStatusCode() != HttpStatus.OK){
            l.info("No work to do.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            workBlockResponse = getWorkBlockResponse();
        }
        return workBlockResponse.getBody();
    }
}
