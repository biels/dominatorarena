package com.biel.dominatorarena.api.responses;

/**
 * Created by Biel on 4/12/2016.
 */
public class PlayerResponse {
    private Long strategyId;

    public PlayerResponse(Long strategyId) {
        this.strategyId = strategyId;
    }

    protected PlayerResponse() {
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }
}
