package com.biel.dominatorarena.api.requests;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Biel on 9/12/2016.
 */
public class BattleCreationRequest {
    boolean usingStrategyId = false;
    boolean allVsFirst = false;
    List<Long> ids;

    public BattleCreationRequest() {
        super();
    }

    public BattleCreationRequest(boolean usingStrategyId) {
        this.usingStrategyId = usingStrategyId;
    }

    public BattleCreationRequest(boolean usingStrategyId, List<Integer> ids) {
        this.usingStrategyId = usingStrategyId;
        this.ids = ids.stream().mapToLong(value -> value).boxed().collect(Collectors.toList());
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

    public boolean isAllVsFirst() {
        return allVsFirst;
    }

    public void setAllVsFirst(boolean allVsFirst) {
        this.allVsFirst = allVsFirst;
    }
}
