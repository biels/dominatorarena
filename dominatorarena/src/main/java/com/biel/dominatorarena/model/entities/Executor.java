package com.biel.dominatorarena.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * A registered game executor
 */
@Entity
public class Executor {
    @Id
    @GeneratedValue
    private Long id;

    String operatingSystem;
    double cpuFrequency;
    int cpuCoreCount;
    double memorySize;

    @OneToMany(mappedBy = "executor")
    List<WorkBlock> workBlocks;

    protected Executor(){
    }
    public Executor(String operatingSystem, double cpuFrequency, int cpuCoreCount, double memorySize) {
        this.operatingSystem = operatingSystem;
        this.cpuFrequency = cpuFrequency;
        this.cpuCoreCount = cpuCoreCount;
        this.memorySize = memorySize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public double getCpuFrequency() {
        return cpuFrequency;
    }

    public void setCpuFrequency(double cpuFrequency) {
        this.cpuFrequency = cpuFrequency;
    }

    public int getCpuCoreCount() {
        return cpuCoreCount;
    }

    public void setCpuCoreCount(int cpuCoreCount) {
        this.cpuCoreCount = cpuCoreCount;
    }

    public double getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(double memorySize) {
        this.memorySize = memorySize;
    }

    public List<WorkBlock> getWorkBlocks() {
        return workBlocks;
    }

    public void setWorkBlocks(List<WorkBlock> workBlocks) {
        this.workBlocks = workBlocks;
    }
}
