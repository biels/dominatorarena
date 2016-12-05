package com.biel.dominatorarena.model.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Biel on 29/11/2016.
 */
@Entity
public class Configuration {
    @Id
    @GeneratedValue
    private Long id;

    String name;

    String contents;

    @ManyToMany(mappedBy = "configurations")
    List<StatisticBattle> statisticBattles;

    @OneToMany(mappedBy = "configuration")
    List<Battle> battles;

    protected Configuration() {
    }

    public Configuration(String name, String contents) {
        this.name = name;
        this.contents = contents;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<StatisticBattle> getStatisticBattles() {
        return statisticBattles;
    }

    public void setStatisticBattles(List<StatisticBattle> statisticBattles) {
        this.statisticBattles = statisticBattles;
    }
}
