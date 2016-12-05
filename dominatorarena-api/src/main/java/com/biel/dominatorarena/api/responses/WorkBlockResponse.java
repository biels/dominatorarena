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

    public WorkBlockResponse(List<ConfigurationResponse> configurationResponses, List<StrategyVersionResponse> strategyVersionResponses, List<BattleResponse> battleResponses) {
        this.configurationResponses = configurationResponses;
        this.strategyVersionResponses = strategyVersionResponses;
        this.battleResponses = battleResponses;
    }
}
