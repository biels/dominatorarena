package com.biel.dominatorarena.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * The result of a specific player in a game
 */
@Entity
public class BattlePlayerResult {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    BattlePlayer battlePlayer;

    Integer score;
    Integer place;
    double cpuTime;
    //Kills, bonusPicked, ...


    protected BattlePlayerResult() {
    }

    public BattlePlayerResult(BattlePlayer battlePlayer) {
        this.battlePlayer = battlePlayer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BattlePlayer getBattlePlayer() {
        return battlePlayer;
    }

    public void setBattlePlayer(BattlePlayer battlePlayer) {
        this.battlePlayer = battlePlayer;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public double getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(double cpuTime) {
        this.cpuTime = cpuTime;
    }
}
