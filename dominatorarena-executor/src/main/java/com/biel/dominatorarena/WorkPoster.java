package com.biel.dominatorarena;

import com.biel.dominatorarena.api.requests.WorkBlockResultRequest;
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
public class WorkPoster {
    Logger l = LoggerFactory.getLogger(WorkPoster.class);
    @Autowired
    LocalInfo localInfo;

    boolean postWork(WorkBlockResultRequest workBlockResultRequest) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(localInfo.getMyUri() + "/work", workBlockResultRequest, String.class);
        boolean ok = responseEntity.getStatusCode() == HttpStatus.OK;
        if(!ok)l.error("Could not post work. Got status code: " + responseEntity.getStatusCode().toString());
        return ok;
    }
}
