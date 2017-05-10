package com.biel.dominatorarena.logic;

import com.biel.dominatorarena.model.entities.*;
import com.biel.dominatorarena.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Biel on 4/12/2016.
 */
@Component
public class RandomWorkGenerator {
    @Autowired
    BattlePlayerRepository battlePlayerRepository;
    @Autowired
    BattleRepository battleRepository;
    @Autowired
    StatisticBattleRepository statisticBattleRepository;
    @Autowired
    private WorkBlockRepository workBlockRepository;
    @Autowired
    private ExecutorRepository executorRepository;

    int nextBattleIndex = 0;

    Random random = new Random();


    private Battle generateMatch(StatisticBattle statisticBattle) {
        //int seed = random.nextInt(100000);

        List<Configuration> configurations = statisticBattle.getConfigurations();
        Configuration configuration = configurations.get(random.nextInt(configurations.size()));
        Battle battle = battleRepository.save(new Battle(statisticBattle, configuration));
        battle.setSeed((int) (battle.getId() % Integer.MAX_VALUE));
        List<BattlePlayer> battlePlayers = statisticBattle.getStrategyVersions().stream()
                .map(BattlePlayer::new)
                .map(mp -> {
                    mp.setBattle(battle);
                    return mp;
                })
                .map(mp -> battlePlayerRepository.save(mp))
                .collect(Collectors.toList());
        Collections.shuffle(battlePlayers);
        if (battlePlayers.size() > 4) {
            battlePlayers = battlePlayers.subList(0, 3);
        }

        while (battlePlayers.size() < 4) {
            BattlePlayer battlePlayer = new BattlePlayer(battlePlayers.get(battlePlayers.size() - 1).getStrategyVersion());
            battlePlayer.setBattle(battle);
            battlePlayers.add(battlePlayerRepository.save(battlePlayer));
        }
        battle.setBattlePlayers(battlePlayers);
        assert (battle.getBattlePlayers().size() == 4);
        return battleRepository.save(battle);
    }

    private WorkBlock generateWorkBlock(StatisticBattle statisticBattle, int n) {
        List<Battle> battles = new ArrayList<>();
        WorkBlock workBlock = workBlockRepository.save(new WorkBlock(new HashSet<>(battles)));
        for (int i = 0; i < n; i++) {
            Battle battle = generateMatch(statisticBattle);
            battle.setWorkBlock(workBlock);
            battleRepository.save(battle);
            battles.add(battle);
        }
        workBlock.setBattles(new HashSet<>(battles));
        return workBlock;
    }

    public Optional<WorkBlock> generateNextWorkBlock(int n) {
        List<StatisticBattle> activeBattles = statisticBattleRepository.findByActiveTrue();
        int maxBattleIndex = activeBattles.size() - 1;
        if (maxBattleIndex < 0) return Optional.empty();
        if (nextBattleIndex > maxBattleIndex) nextBattleIndex = 0;
        return Optional.ofNullable(generateWorkBlock(activeBattles.get(nextBattleIndex++), n));
    }
}
