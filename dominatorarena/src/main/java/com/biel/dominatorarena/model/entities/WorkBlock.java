package com.biel.dominatorarena.model.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Biel on 4/12/2016.
 */
@Entity
public class WorkBlock {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    Executor executor;

    boolean executed;

    @OneToMany(mappedBy = "workBlock")
    Set<Battle> battles;

    protected WorkBlock() {
    }

    public WorkBlock(Set<Battle> battles) {
        this.battles = battles;
    }

    public WorkBlock(Executor executor, boolean executed, Set<Battle> battles) {
        this.executor = executor;
        this.executed = executed;
        this.battles = battles;
    }

    public enum ExecutionStatus{EXECUTED, ASSIGNED, NOT_ASSIGNED}
    public ExecutionStatus getExecutionStatus(){
        if(executed) return ExecutionStatus.EXECUTED;
        return executor == null ? ExecutionStatus.NOT_ASSIGNED : ExecutionStatus.ASSIGNED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public Set<Battle> getBattles() {
        return battles;
    }

    public void setBattles(Set<Battle> battles) {
        this.battles = battles;
    }

}
