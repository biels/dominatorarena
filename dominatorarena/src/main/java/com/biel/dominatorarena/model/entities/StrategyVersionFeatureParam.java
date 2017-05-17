package com.biel.dominatorarena.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Biel on 15/5/2017.
 */
@Entity
public class StrategyVersionFeatureParam {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String value;

    @ManyToOne
    @JsonIgnore
    private
    StrategyVersionFeature feature;

    protected StrategyVersionFeatureParam() {
    }

    public StrategyVersionFeatureParam(StrategyVersionFeature feature, String name, String value) {
        this.name = name;
        this.value = value;
        this.feature = feature;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StrategyVersionFeature getFeature() {
        return feature;
    }

    public void setFeature(StrategyVersionFeature feature) {
        this.feature = feature;
    }
}
