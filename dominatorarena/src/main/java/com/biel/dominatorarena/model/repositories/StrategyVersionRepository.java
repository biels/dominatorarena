package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.StatisticBattle;
import com.biel.dominatorarena.model.entities.Strategy;
import com.biel.dominatorarena.model.entities.StrategyVersion;
import com.biel.dominatorarena.model.entities.WorkBlock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Biel on 28/11/2016.
 */
public interface StrategyVersionRepository extends CrudRepository<StrategyVersion, Long> {
    List<StrategyVersion> findAll();
    Optional<StrategyVersion> findOneByDigest(byte[] digest);
    List<StrategyVersion> findByStatisticBattles(StatisticBattle statisticBattle);
    List<StrategyVersion> findByStatisticBattles_Battles_WorkBlock(WorkBlock workBlock);
    StrategyVersion findTopByStrategyOrderByIdDesc(Strategy strategy);

}
