package com.biel.dominatorarena.api.requests;

/**
 * Created by Biel on 8/5/2017.
 */
public class BattlePlayerResultRequest {
    private int score;
    private int cpuTime;

    public BattlePlayerResultRequest() {
    }

    public BattlePlayerResultRequest(int score, int cpuTime) {
        this.score = score;
        this.cpuTime = cpuTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(int cpuTime) {
        this.cpuTime = cpuTime;
    }
}
