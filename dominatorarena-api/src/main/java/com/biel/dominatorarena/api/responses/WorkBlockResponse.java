package com.biel.dominatorarena.api.responses;

import java.util.List;

/**
 * Created by Biel on 4/12/2016.
 */
public class WorkBlockResponse {
    //Files
    List<ConfigurationResponse> configurationResponses;
    List<StrategyVersionResponse> strategyVersionResponses;
    //Matches
    List<BattleResponse> battleResponses;

    protected WorkBlockResponse() {
    }

    public WorkBlockResponse(List<ConfigurationResponse> configurationResponses, List<StrategyVersionResponse> strategyVersionResponses, List<BattleResponse> battleResponses) {
        this.configurationResponses = configurationResponses;
        this.strategyVersionResponses = strategyVersionResponses;
        this.battleResponses = battleResponses;
    }

    public List<ConfigurationResponse> getConfigurationResponses() {
        return configurationResponses;
    }

    public void setConfigurationResponses(List<ConfigurationResponse> configurationResponses) {
        this.configurationResponses = configurationResponses;
    }

    public List<StrategyVersionResponse> getStrategyVersionResponses() {
        return strategyVersionResponses;
    }

    public void setStrategyVersionResponses(List<StrategyVersionResponse> strategyVersionResponses) {
        this.strategyVersionResponses = strategyVersionResponses;
    }

    public List<BattleResponse> getBattleResponses() {
        return battleResponses;
    }

    public void setBattleResponses(List<BattleResponse> battleResponses) {
        this.battleResponses = battleResponses;
    }

    @Override
    public String toString() {
        return "WorkBlockResponse{" +
                "configurationResponses=" + configurationResponses +
                ", strategyVersionResponses=" + strategyVersionResponses +
                ", battleResponses=" + battleResponses +
                '}';
    }
}
