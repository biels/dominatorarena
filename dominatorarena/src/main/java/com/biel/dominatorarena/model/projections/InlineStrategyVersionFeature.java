package com.biel.dominatorarena.model.projections;

import com.biel.dominatorarena.model.entities.StrategyVersionFeature;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Created by Biel on 15/5/2017.
 */
@Projection(types = {StrategyVersionFeature.class})
public interface InlineStrategyVersionFeature {
    String getName();
    String getAlias();
    List<StrategyVersionFeature> getFeatures();
}
