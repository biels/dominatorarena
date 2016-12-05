package com.biel.dominatorarena.api.requests;

/**
 * Created by Biel on 30/11/2016.
 */
public class RegisterWorkerRequest {
    private int cpuCoreCount;
    private double memorySize;

    protected RegisterWorkerRequest(){

    }
    public RegisterWorkerRequest(int cpuCoreCount, double memorySize) {
        this.cpuCoreCount = cpuCoreCount;
        this.memorySize = memorySize;
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
}
