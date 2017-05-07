package com.biel.dominatorarena.api.requests;

import java.util.List;

/**
 * Created by Biel on 4/12/2016.
 */
public class WorkBlockResultRequest {
    int workBlockId;
    List<BattleResultRequest> battleResultRequests;

    protected WorkBlockResultRequest() {
    }

    public WorkBlockResultRequest(List<BattleResultRequest> battleResultRequests) {
        this.battleResultRequests = battleResultRequests;
    }

    public int getWorkBlockId() {
        return workBlockId;
    }

    public void setWorkBlockId(int workBlockId) {
        this.workBlockId = workBlockId;
    }

    public List<BattleResultRequest> getBattleResultRequests() {
        return battleResultRequests;
    }

    public void setBattleResultRequests(List<BattleResultRequest> battleResultRequests) {
        this.battleResultRequests = battleResultRequests;
    }
}
