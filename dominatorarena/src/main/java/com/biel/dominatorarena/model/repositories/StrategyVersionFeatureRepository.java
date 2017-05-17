package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.StrategyVersionFeature;
import com.biel.dominatorarena.model.projections.InlineStrategyVersionFeature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Biel on 15/5/2017.
 */
@RepositoryRestResource(exported = false, excerptProjection = InlineStrategyVersionFeature.class)
public interface StrategyVersionFeatureRepository extends CrudRepository<StrategyVersionFeature, Long> {
}
