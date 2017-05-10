package com.biel.dominatorarena.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * The result of the battle itself (to complete)
 */
@Entity
public class BattleResult {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    Battle battle;

    @OneToOne
    BattlePlayer winner;

    public BattleResult() {
    }

    public BattleResult(BattlePlayer winner) {
        this.winner = winner;
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

    public BattlePlayer getWinner() {
        return winner;
    }

    public void setWinner(BattlePlayer winner) {
        this.winner = winner;
    }
}
