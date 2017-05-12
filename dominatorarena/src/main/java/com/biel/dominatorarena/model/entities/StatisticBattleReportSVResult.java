package com.biel.dominatorarena.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Biel on 10/5/2017.
 */
@Entity
public class StatisticBattleReportSVResult {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    private StatisticBattleReport statisticBattleReport;

    @ManyToOne
    private StrategyVersion strategyVersion;

    private double inGamePlayerMultiplicity;

    /*
       + Max Min
       + Mean, Standard deviation
       + Winratio
       + Jutge winratio (1st and 2nd places count as win)
       + CPU time / player, total
    */
    private int maxScore;
    private int minScore;
    private double averageScore;
    private double scoreStandardDeviation;
    private double winRatio;
    private double jutgeWinRatio;
    private double cpuTime;
    private double averagePlace;

    public StatisticBattleReportSVResult() {
    }

    public StatisticBattleReportSVResult(StatisticBattleReport statisticBattleReport, StrategyVersion strategyVersion) {
        this.statisticBattleReport = statisticBattleReport;
        this.strategyVersion = strategyVersion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatisticBattleReport getStatisticBattleReport() {
        return statisticBattleReport;
    }

    public void setStatisticBattleReport(StatisticBattleReport statisticBattleReport) {
        this.statisticBattleReport = statisticBattleReport;
    }

    public StrategyVersion getStrategyVersion() {
        return strategyVersion;
    }

    public void setStrategyVersion(StrategyVersion strategyVersion) {
        this.strategyVersion = strategyVersion;
    }

    public double getInGamePlayerMultiplicity() {
        return inGamePlayerMultiplicity;
    }

    public void setInGamePlayerMultiplicity(double inGamePlayerMultiplicity) {
        this.inGamePlayerMultiplicity = inGamePlayerMultiplicity;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public double getScoreStandardDeviation() {
        return scoreStandardDeviation;
    }

    public void setScoreStandardDeviation(double scoreStandardDeviation) {
        this.scoreStandardDeviation = scoreStandardDeviation;
    }

    public double getWinRatio() {
        return winRatio;
    }

    public void setWinRatio(double winRatio) {
        this.winRatio = winRatio;
    }

    public double getJutgeWinRatio() {
        return jutgeWinRatio;
    }

    public void setJutgeWinRatio(double jutgeWinRatio) {
        this.jutgeWinRatio = jutgeWinRatio;
    }

    public double getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(double cpuTime) {
        this.cpuTime = cpuTime;
    }

    public double getAveragePlace() {
        return averagePlace;
    }

    public void setAveragePlace(double place) {
        this.averagePlace = place;
    }

    public double getEffectiveness(){
        double wrEqual = inGamePlayerMultiplicity / (double) 4;
        return (winRatio - wrEqual) / wrEqual;
    }
}
