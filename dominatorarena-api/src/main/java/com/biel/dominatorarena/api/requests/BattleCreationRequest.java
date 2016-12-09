package com.biel.dominatorarena.api.requests;

import java.util.List;

/**
 * Created by Biel on 9/12/2016.
 */
public class BattleCreationRequest {
    boolean usingStrategyId = false;
    List<Long> ids;

    public BattleCreationRequest(boolean usingStrategyId, List<Long> ids) {
        this.usingStrategyId = usingStrategyId;
        this.ids = ids;
    }

    public boolean isUsingStrategyId() {
        return usingStrategyId;
    }

    public void setUsingStrategyId(boolean usingStrategyId) {
        this.usingStrategyId = usingStrategyId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
