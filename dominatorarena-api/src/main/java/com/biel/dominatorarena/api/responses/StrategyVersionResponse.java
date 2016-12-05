package com.biel.dominatorarena.api.responses;

/**
 * Created by Biel on 4/12/2016.
 */
public class StrategyVersionResponse {
    Long serverId;
    String code;
    //byte[] compiled;

    public StrategyVersionResponse(Long serverId, String code) {
        this.serverId = serverId;
        this.code = code;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
