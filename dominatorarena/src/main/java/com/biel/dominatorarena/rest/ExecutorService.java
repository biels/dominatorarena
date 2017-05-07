package com.biel.dominatorarena.rest;


import com.biel.dominatorarena.api.requests.RegisterWorkerRequest;
import com.biel.dominatorarena.model.entities.Executor;
import com.biel.dominatorarena.model.repositories.ExecutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

/**
 * Created by Biel on 29/11/2016.
 */
@RestController
@RequestMapping("/executors")
public class ExecutorService {
    @Autowired
    ExecutorRepository executorRepository;

    @RequestMapping(method = RequestMethod.GET)
    List<Executor> list(){
        return executorRepository.findAll();
    }
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> register(@RequestBody RegisterWorkerRequest input){
        Executor result = executorRepository.save(new Executor("Unknown", 0, input.getCpuCoreCount(), input.getMemorySize()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
