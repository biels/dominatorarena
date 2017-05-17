package com.biel.dominatorarena.api.responses;

/**
 * Created by Biel on 4/12/2016.
 */
public class PlayerResponse {
    private Long strategyId;
    private int slot;

    public PlayerResponse(Long strategyId, int slot) {
        this.strategyId = strategyId;
        this.slot = slot;
    }

    protected PlayerResponse() {
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
