package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.StrategyVersionFeatureParam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Biel on 15/5/2017.
 */
@RepositoryRestResource(exported = false)
public interface StrategyVersionFeatureParamRepository extends CrudRepository<StrategyVersionFeatureParam, Long> {
}
