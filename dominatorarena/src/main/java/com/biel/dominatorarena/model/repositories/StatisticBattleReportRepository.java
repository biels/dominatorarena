package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.StatisticBattle;
import com.biel.dominatorarena.model.entities.StatisticBattleReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Biel on 10/5/2017.
 */
@RepositoryRestResource(exported = false)
public interface StatisticBattleReportRepository extends CrudRepository<StatisticBattleReport, Long> {
    @Transactional
    void removeAllByStatisticBattle_Id(Long id);
}
