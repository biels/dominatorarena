package com.biel.dominatorarena.model.projections;

import com.biel.dominatorarena.model.entities.StrategyVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Biel on 12/5/2017.
 */
@Projection(types = {StrategyVersion.class})
public interface InlineStrategyVersion {
    @Value("#{target.getStrategy().name}")
    String getStrategyName();
}
