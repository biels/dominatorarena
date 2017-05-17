package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.StatisticBattle;
import com.biel.dominatorarena.model.entities.Strategy;
import com.biel.dominatorarena.model.entities.StrategyVersion;
import com.biel.dominatorarena.model.entities.WorkBlock;
import com.biel.dominatorarena.model.projections.InlineStrategyVersion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

/**
 * Created by Biel on 28/11/2016.
 */
@RepositoryRestResource()
@CrossOrigin
public interface StrategyVersionRepository extends CrudRepository<StrategyVersion, Long> {
    List<StrategyVersion> findAll();
    List<StrategyVersion> findByStrategy(Strategy strategy);
    Optional<StrategyVersion> findOneByStrategy_Name(String strategyName);
    Optional<StrategyVersion> findOneByDigest(byte[] digest);
    List<StrategyVersion> findByStatisticBattles(StatisticBattle statisticBattle);
    List<StrategyVersion> findByStatisticBattles_Battles_WorkBlock(WorkBlock workBlock);
    StrategyVersion findTopByStrategyOrderByIdDesc(Strategy strategy);

}
