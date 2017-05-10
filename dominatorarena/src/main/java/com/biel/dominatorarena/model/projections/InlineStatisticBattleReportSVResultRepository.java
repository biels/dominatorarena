package com.biel.dominatorarena.model.projections;

import com.biel.dominatorarena.model.entities.StatisticBattleReportSVResult;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Biel on 10/5/2017.
 */
@Projection(types = {StatisticBattleReportSVResult.class})
public interface InlineStatisticBattleReportSVResultRepository {

}
