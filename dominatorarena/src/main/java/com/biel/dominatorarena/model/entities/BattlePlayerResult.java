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
    Integer cellsPainted;
    Integer cellsLost;

    Integer witchKills;
    Integer knightKills;
    Integer knightDeaths;
    Integer farmerDeaths;


    //Integer farmersKilledByWitch;
    //Integer farmersLostByWitch;


    protected BattlePlayerResult() {
    }

    public BattlePlayerResult(BattlePlayer battlePlayer, Integer score, Integer cellsPainted, Integer cellsLost, Integer witchKills, Integer knightKills, Integer knightDeaths, Integer farmerDeaths) {
        this.battlePlayer = battlePlayer;
        this.score = score;
        this.cellsPainted = cellsPainted;
        this.cellsLost = cellsLost;
        this.witchKills = witchKills;
        this.knightKills = knightKills;
        this.knightDeaths = knightDeaths;
        this.farmerDeaths = farmerDeaths;
    }

    public BattlePlayerResult(BattlePlayer battlePlayer, Integer score) {
        this.battlePlayer = battlePlayer;
        this.score = score;
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

    public Integer getCellsPainted() {
        return cellsPainted;
    }

    public void setCellsPainted(Integer cellsPainted) {
        this.cellsPainted = cellsPainted;
    }

    public Integer getCellsLost() {
        return cellsLost;
    }

    public void setCellsLost(Integer cellsLost) {
        this.cellsLost = cellsLost;
    }

    public Integer getWitchKills() {
        return witchKills;
    }

    public void setWitchKills(Integer witchKills) {
        this.witchKills = witchKills;
    }

    public Integer getKnightKills() {
        return knightKills;
    }

    public void setKnightKills(Integer knightKills) {
        this.knightKills = knightKills;
    }

    public Integer getKnightDeaths() {
        return knightDeaths;
    }

    public void setKnightDeaths(Integer knightDeaths) {
        this.knightDeaths = knightDeaths;
    }

    public Integer getFarmerDeaths() {
        return farmerDeaths;
    }

    public void setFarmerDeaths(Integer farmerDeaths) {
        this.farmerDeaths = farmerDeaths;
    }
}
