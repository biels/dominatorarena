package com.biel.dominatorarena.model.entities;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

/**
 * A possible configuration for a game
 */
@Entity
public class Configuration {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    String name;

    String mapName;
    int nbPlayers;
    int nbBikes;
    int nbRounds;
    int bonusRound;
    int turboDuration;
    int ghostDuration;
    int scoreBonus;

    @ManyToMany(mappedBy = "configurations")
    List<StatisticBattle> statisticBattles;

    @OneToMany(mappedBy = "configuration")
    List<Battle> battles;

    protected Configuration() {
    }

    public Configuration(String name) {
        this.name = name;
        mapName = "cube";
        nbPlayers = 4;
        nbBikes = 2;
        nbRounds = 200;
        bonusRound = 50;
        turboDuration = 8;
        ghostDuration = 3;
        scoreBonus = 50;
    }

    public Configuration(String name, String mapName, int nbPlayers, int nbBikes, int nbRounds, int bonusRound, int turboDuration, int ghostDuration, int scoreBonus) {
        this.name = name;
        this.mapName = mapName;
        this.nbPlayers = nbPlayers;
        this.nbBikes = nbBikes;
        this.nbRounds = nbRounds;
        this.bonusRound = bonusRound;
        this.turboDuration = turboDuration;
        this.ghostDuration = ghostDuration;
        this.scoreBonus = scoreBonus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public void setNbPlayers(int nbPlayers) {
        this.nbPlayers = nbPlayers;
    }

    public int getNbBikes() {
        return nbBikes;
    }

    public void setNbBikes(int nbBikes) {
        this.nbBikes = nbBikes;
    }

    public int getNbRounds() {
        return nbRounds;
    }

    public void setNbRounds(int nbRounds) {
        this.nbRounds = nbRounds;
    }

    public int getBonusRound() {
        return bonusRound;
    }

    public void setBonusRound(int bonusRound) {
        this.bonusRound = bonusRound;
    }

    public int getTurboDuration() {
        return turboDuration;
    }

    public void setTurboDuration(int turboDuration) {
        this.turboDuration = turboDuration;
    }

    public int getGhostDuration() {
        return ghostDuration;
    }

    public void setGhostDuration(int ghostDuration) {
        this.ghostDuration = ghostDuration;
    }

    public int getScoreBonus() {
        return scoreBonus;
    }

    public void setScoreBonus(int scoreBonus) {
        this.scoreBonus = scoreBonus;
    }

    public List<StatisticBattle> getStatisticBattles() {
        return statisticBattles;
    }

    public void setStatisticBattles(List<StatisticBattle> statisticBattles) {
        this.statisticBattles = statisticBattles;
    }

    public List<Battle> getBattles() {
        return battles;
    }

    public void setBattles(List<Battle> battles) {
        this.battles = battles;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "name='" + name + '\'' +
                ", nbPlayers=" + nbPlayers +
                ", nbBikes=" + nbBikes +
                ", nbRounds=" + nbRounds +
                ", bonusRound=" + bonusRound +
                ", turboDuration=" + turboDuration +
                ", ghostDuration=" + ghostDuration +
                ", scoreBonus=" + scoreBonus +
                '}';
    }
    public String toConfigFileContent(){
        return "tron3d v1.0\n" +
                "map " + mapName + "\n"+
                "nb_players\t" + nbPlayers + "\n" +
                "nb_bikes\t" + nbBikes + "\n" +
                "nb_rounds\t" + nbRounds + "\n" +
                "bonus_round " + bonusRound + "\n" +
                "turbo_duration " + turboDuration + "\n" +
                "ghost_duration " + ghostDuration + "\n" +
                "score_bonus " + scoreBonus + "\n" +
                "secgame false\n" +
                "names ? ? ? ?\n" +
                "\n" +
                "round -1\n" +
                "score 0 0 0 0\n" +
                "status 0 0 0 0\n";
    }
}
