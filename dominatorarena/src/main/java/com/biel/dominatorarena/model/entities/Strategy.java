package com.biel.dominatorarena.model.entities;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * An approach to beat the game, a type of player
 */
@Entity
public class Strategy {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private String name;

    @OneToMany(mappedBy = "strategy")
    private List<StrategyVersion> versions;

    protected Strategy(){
    }
    public Strategy(String name) {
        this.name = name;
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

    public List<StrategyVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<StrategyVersion> versions) {
        this.versions = versions;
    }
}
