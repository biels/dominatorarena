package com.biel.dominatorarena.model.repositories;

import com.biel.dominatorarena.model.entities.Configuration;
import com.biel.dominatorarena.model.entities.StatisticBattle;
import com.biel.dominatorarena.model.entities.WorkBlock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Biel on 29/11/2016.
 */
//@RepositoryRestResource
public interface ConfigurationRepository extends CrudRepository<Configuration, Long> {
    List<Configuration> findAll();
    List<Configuration> findByStatisticBattles(StatisticBattle statisticBattle);
    //List<Configuration> findByBattles_WorkBlock(WorkBlock workBlock);
   // @Query("SELECT c from Configuration c WHERE  c.battles ")
   // List<Configuration> findByWorkBlock(WorkBlock workBlock);
}
