package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.StatisticBattleReportSVResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Biel on 10/5/2017.
 */
@RepositoryRestResource(exported = false)
public interface StatisticBattleReportSVResultRepository extends CrudRepository<StatisticBattleReportSVResult, Long> {
}
