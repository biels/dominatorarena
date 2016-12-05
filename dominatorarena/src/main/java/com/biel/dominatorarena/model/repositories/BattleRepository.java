package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.Battle;
import com.biel.dominatorarena.model.entities.StatisticBattle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Biel on 29/11/2016.
 */
public interface BattleRepository extends CrudRepository<Battle, Long> {
    List<Battle> findAllByStatisticBattle(StatisticBattle statisticBattle);
}
