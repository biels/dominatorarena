package com.biel.dominatorarena.rest;

import com.biel.dominatorarena.api.requests.BattleCreationRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Biel on 7/5/2017.
 */
@RestController
@RequestMapping("/service/sample")
public class SampleService {
    @RequestMapping
    String index(){
        return "Sample serialized responses";
    }
    @RequestMapping(path = "battleCreationRequest", method = RequestMethod.GET)
    BattleCreationRequest battleCreationRequestSample(){
        return new BattleCreationRequest(true,
                Stream.iterate(0, t -> t+1).limit(4).collect(Collectors.toList()));
    }
}
