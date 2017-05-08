package com.biel.dominatorarena.api.requests;

import java.util.List;

/**
 * Created by Biel on 4/12/2016.
 */
public class BattleResultRequest {
    //TODO Define request
    Long battleId;
    //Ordered as in BattleResponse
    List<BattlePlayerResultRequest> battlePlayerResultRequests;

    public BattleResultRequest() {
    }

    public BattleResultRequest(Long battleId, List<BattlePlayerResultRequest> battlePlayerResultRequests) {
        this.battleId = battleId;
        this.battlePlayerResultRequests = battlePlayerResultRequests;
    }

    public Long getBattleId() {
        return battleId;
    }

    public void setBattleId(Long battleId) {
        this.battleId = battleId;
    }

    public List<BattlePlayerResultRequest> getBattlePlayerResultRequests() {
        return battlePlayerResultRequests;
    }

    public void setBattlePlayerResultRequests(List<BattlePlayerResultRequest> battlePlayerResultRequests) {
        this.battlePlayerResultRequests = battlePlayerResultRequests;
    }
}
