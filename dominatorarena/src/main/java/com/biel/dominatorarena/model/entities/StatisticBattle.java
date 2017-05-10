package com.biel.dominatorarena.model.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * A battle represented by multiple games
 */
@Entity
public class StatisticBattle {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable
    private List<StrategyVersion> strategyVersions;

    @ManyToMany
    @JoinTable
    private List<Configuration> configurations;

    @OneToMany(mappedBy = "statisticBattle")
    private List<Battle> battles;

    private boolean active = false;

    @OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private StatisticBattleReport report;

    protected StatisticBattle() {
    }

    public StatisticBattle(List<StrategyVersion> strategyVersions, List<Configuration> configurations) {
        this.strategyVersions = strategyVersions;
        this.configurations = configurations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<StrategyVersion> getStrategyVersions() {
        return strategyVersions;
    }

    public void setStrategyVersions(List<StrategyVersion> strategyVersions) {
        this.strategyVersions = strategyVersions;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    public List<Battle> getBattles() {
        return battles;
    }

    public void setBattles(List<Battle> battles) {
        this.battles = battles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public StatisticBattleReport getReport() {
        return report;
    }

    public void setReport(StatisticBattleReport report) {
        this.report = report;
    }
}
