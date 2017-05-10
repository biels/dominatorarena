package com.biel.dominatorarena.model.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Biel on 10/5/2017.
 */
@Entity
public class StatisticBattleReport {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "report")
    StatisticBattle statisticBattle;

    int battleCount;
    //Metrics (from battles)
    /*
        + Max Min
        + Mean, Standard deviation
        + Winratio
        + Jutge winratio (1st and 2nd places count as win)
        + CPU time / player, total
     */
    @OneToMany(mappedBy = "statisticBattleReport", cascade = CascadeType.ALL, orphanRemoval = true)
    List<StatisticBattleReportSVResult> svResults;

    //Interesting global stats


    protected StatisticBattleReport() {
    }

    public StatisticBattleReport(StatisticBattle statisticBattle) {
        this.statisticBattle = statisticBattle;
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

    public int getBattleCount() {
        return battleCount;
    }

    public void setBattleCount(int battleCount) {
        this.battleCount = battleCount;
    }

    public List<StatisticBattleReportSVResult> getSvResults() {
        return svResults;
    }

    public void setSvResults(List<StatisticBattleReportSVResult> svResults) {
        this.svResults = svResults;
    }
}
