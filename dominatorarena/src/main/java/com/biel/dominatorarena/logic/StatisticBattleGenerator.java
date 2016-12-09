package com.biel.dominatorarena.logic;

import com.biel.dominatorarena.model.entities.Configuration;
import com.biel.dominatorarena.model.entities.StatisticBattle;
import com.biel.dominatorarena.model.entities.Strategy;
import com.biel.dominatorarena.model.entities.StrategyVersion;
import com.biel.dominatorarena.model.repositories.ConfigurationRepository;
import com.biel.dominatorarena.model.repositories.StatisticBattleRepository;
import com.biel.dominatorarena.model.repositories.StrategyRepository;
import com.biel.dominatorarena.model.repositories.StrategyVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Biel on 4/12/2016.
 */
@Component
public class StatisticBattleGenerator {
    @Autowired
    StrategyVersionRepository strategyVersionRepository;
    @Autowired
    StatisticBattleRepository statisticBattleRepository;
    @Autowired
    ConfigurationRepository configurationRepository;
    public StatisticBattle generateStatisticBattleFromStrategies(List<Strategy> strategies){
        List<StrategyVersion> strategyVersions = strategies.stream()
                .filter(strategy -> strategy.getVersions().size() > 0)
                .map(strategy -> strategyVersionRepository.findTopByStrategyOrderByIdDesc(strategy))
                .collect(Collectors.toList());
        return generateStatisticBattleFromVersions(strategyVersions);
    }
    public StatisticBattle generateStatisticBattleFromVersions(List<StrategyVersion> strategyVersions){
        List<Configuration> configurations = configurationRepository.findAll(); //TODO Configuration selection
        return statisticBattleRepository.save(new StatisticBattle(strategyVersions, configurations));
    }
}
