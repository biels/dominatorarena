package com.biel.dominatorarena.model.entities;

import javax.persistence.*;
import java.util.List;

/**
 * A specific played game
 */
@Entity
public class Battle {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private StatisticBattle statisticBattle;

    private Integer seed;

    @ManyToOne
    Configuration configuration;

    @OneToMany(mappedBy = "battle")
    private List<BattlePlayer> battlePlayers;

    @OneToOne
    private BattleResult result;

    @ManyToOne
    private WorkBlock workBlock;

    protected Battle() {
    }


    public Battle(StatisticBattle statisticBattle, Integer seed, Configuration configuration, List<BattlePlayer> battlePlayers) {
        this.statisticBattle = statisticBattle;
        this.seed = seed;
        this.configuration = configuration;
        this.battlePlayers = battlePlayers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatisticBattle getStatisticBattle() {
        return statisticBattle;
    }

    public void setStatisticBattle(StatisticBattle statisticBattle) {
        this.statisticBattle = statisticBattle;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public List<BattlePlayer> getBattlePlayers() {
        return battlePlayers;
    }

    public void setBattlePlayers(List<BattlePlayer> battlePlayers) {
        this.battlePlayers = battlePlayers;
    }

    public BattleResult getResult() {
        return result;
    }

    public void setResult(BattleResult result) {
        this.result = result;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public WorkBlock getWorkBlock() {
        return workBlock;
    }

    public void setWorkBlock(WorkBlock workBlock) {
        this.workBlock = workBlock;
    }
}
