package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.Strategy;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Biel on 28/11/2016.
 */
public interface StrategyRepository extends CrudRepository<Strategy, Long>{
    Collection<Strategy> findAll();
    Optional<Strategy> findOneByName(String name);

}
