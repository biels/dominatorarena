package com.biel.dominatorarena.model.projections;

import com.biel.dominatorarena.model.entities.StatisticBattleReport;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Biel on 15/5/2017.
 */
//@Projection(types = {StatisticBattleReport.class})
public interface StatisticBattleReportProjection {
    boolean getActive();
    boolean isAllVsFirst();
    int getRequestedBattleCount();

}
