package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.Strategy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Biel on 28/11/2016.
 */
@Transactional
public interface StrategyRepository extends CrudRepository<Strategy, Long>{
    Collection<Strategy> findAll();
    Optional<Strategy> findOneByName(String name);

}
