package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.StatisticBattle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Biel on 29/11/2016.
 */
public interface StatisticBattleRepository extends CrudRepository<StatisticBattle, Long> {
    List<StatisticBattle> findByActiveTrue();
}
