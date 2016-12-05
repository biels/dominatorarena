package com.biel.dominatorarena.rest;


import com.biel.dominatorarena.model.entities.Executor;
import com.biel.dominatorarena.model.repositories.ExecutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Biel on 29/11/2016.
 */
@RestController
@RequestMapping("/executors")
public class ExecutorService {
    @Autowired
    ExecutorRepository executorRepository;

    @RequestMapping(method = RequestMethod.GET)
    Collection<Executor> list(){
        return executorRepository.findAll();
    }
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> register(@RequestBody Executor input){

        return ResponseEntity.noContent().build();
    }
}
