package com.biel.dominatorarena.model.entities;

import com.biel.dominatorarena.Config;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

/**
 * Every modification of a Strategy. StrategyVersions play the games
 */
@Entity
public class StrategyVersion {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    Strategy strategy;

    @ManyToMany(mappedBy = "strategyVersions")
    List<StatisticBattle> statisticBattles;
    //Strategy program info

    @NaturalId @Column(length = 16)
    byte[] digest;

    boolean compiled;

    protected StrategyVersion() {
    }

    public StrategyVersion(Strategy strategy, byte[] digest) {
        this.strategy = strategy;
        this.digest = digest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }


    public List<StatisticBattle> getStatisticBattles() {
        return statisticBattles;
    }

    public void setStatisticBattles(List<StatisticBattle> statisticBattles) {
        this.statisticBattles = statisticBattles;
    }

    public byte[] getDigest() {
        return digest;
    }

    public void setDigest(byte[] digest) {
        this.digest = digest;
    }

    public boolean isCompiled() {
        return compiled;
    }

    public void setCompiled(boolean compiled) {
        this.compiled = compiled;
    }

    public File getSourceFile(){
        String ext = ".cpp";
        if(compiled) ext = ".o";
        return new File(Config.VERSION_DIR + "/" + getStrategy().getName() + "_" + getId() + ext);
    }
    public String readSource(){
        Optional<String> reduce = null;
        try {
            reduce = Files.readAllLines(getSourceFile().toPath()).stream().reduce((s, s2) -> s + "\n" + s2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reduce.orElse("File not found");
    }
    public byte[] readCompiledBytes(){
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(getSourceFile().toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
