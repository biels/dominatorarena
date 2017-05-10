package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.Executor;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by Biel on 29/11/2016.
 */
public interface ExecutorRepository extends CrudRepository<Executor, Long> {
    List<Executor> findAll();
    //List<Executor> findByWorkBlocks_();
}
