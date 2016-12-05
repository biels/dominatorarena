package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.BattlePlayer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Biel on 29/11/2016.
 */
public interface BattlePlayerRepository extends CrudRepository<BattlePlayer, Long> {
}
