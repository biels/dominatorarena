package com.biel.dominatorarena.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Biel on 15/5/2017.
 */
@Entity
public class StrategyVersionFeature {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String alias;

    @ManyToOne
    @JsonIgnore
    private StrategyVersion strategyVersion;

    @OneToMany(mappedBy = "feature", cascade = CascadeType.ALL)
    private List<StrategyVersionFeatureParam> params;


    protected StrategyVersionFeature() {
    }

    public StrategyVersionFeature(StrategyVersion strategyVersion, String name) {
        this.name = name;
        this.strategyVersion = strategyVersion;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public StrategyVersion getStrategyVersion() {
        return strategyVersion;
    }

    public void setStrategyVersion(StrategyVersion strategyVersion) {
        this.strategyVersion = strategyVersion;
    }

    public List<StrategyVersionFeatureParam> getParams() {
        return params;
    }

    public void setParams(List<StrategyVersionFeatureParam> params) {
        this.params = params;
    }
}
