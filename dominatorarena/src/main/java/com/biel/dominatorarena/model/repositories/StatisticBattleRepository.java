package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.StatisticBattle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * Created by Biel on 29/11/2016.
 */
@CrossOrigin
public interface StatisticBattleRepository extends CrudRepository<StatisticBattle, Long> {
    List<StatisticBattle> findByActiveTrue();
}
