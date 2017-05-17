package com.biel.dominatorarena.model.projections;

import com.biel.dominatorarena.model.entities.StrategyVersion;
import com.biel.dominatorarena.model.entities.StrategyVersionFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Created by Biel on 12/5/2017.
 */
@Projection(types = {StrategyVersion.class})
public interface InlineStrategyVersion {
    Long getId();

    @Value("#{target.getStrategy().name}")
    String getStrategyName();

    @Value("#{target.getFeatures()}")
    List<StrategyVersionFeature> getFeatures();
}
