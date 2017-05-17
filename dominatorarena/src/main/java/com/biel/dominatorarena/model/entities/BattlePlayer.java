package com.biel.dominatorarena.model.entities;

import javax.persistence.*;

/**
 * A player in a specific game
 */
@Entity
public class BattlePlayer {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    Battle battle;

    @ManyToOne
    StrategyVersion strategyVersion;

    int slot;

    @OneToOne
    BattlePlayerResult result;

    protected BattlePlayer() {
    }

    public BattlePlayer(StrategyVersion strategyVersion) {
        this.strategyVersion = strategyVersion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public StrategyVersion getStrategyVersion() {
        return strategyVersion;
    }

    public void setStrategyVersion(StrategyVersion strategyVersion) {
        this.strategyVersion = strategyVersion;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public BattlePlayerResult getResult() {
        return result;
    }

    public void setResult(BattlePlayerResult result) {
        this.result = result;
    }
}
