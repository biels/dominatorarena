package com.biel.dominatorarena.api.responses;

import java.util.List;

/**
 * Created by Biel on 4/12/2016.
 */
public class BattleResponse {
    private Long battleId;
    private Integer seed;
    private Long configurationId;
    private List<PlayerResponse> playerResponses;

    public BattleResponse(Long battleId, Integer seed, Long configurationId, List<PlayerResponse> playerResponses) {
        this.battleId = battleId;
        this.seed = seed;
        this.configurationId = configurationId;
        this.playerResponses = playerResponses;
    }

    protected BattleResponse() {
    }

    public Long getBattleId() {
        return battleId;
    }

    public void setBattleId(Long battleId) {
        this.battleId = battleId;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public Long getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(Long configurationId) {
        this.configurationId = configurationId;
    }

    public List<PlayerResponse> getPlayerResponses() {
        return playerResponses;
    }

    public void setPlayerResponses(List<PlayerResponse> playerResponses) {
        this.playerResponses = playerResponses;
    }
}
