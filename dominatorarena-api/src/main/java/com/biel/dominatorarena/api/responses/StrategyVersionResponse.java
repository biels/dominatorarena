package com.biel.dominatorarena.api.responses;

/**
 * Created by Biel on 4/12/2016.
 */
public class StrategyVersionResponse {
    Long serverId;
    String code;
    String name;
    byte[] compiled;


    public StrategyVersionResponse(Long serverId, String name) {
        this.serverId = serverId;
        this.name = name;
    }

    protected StrategyVersionResponse() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getCompiled() {
        return compiled;
    }

    public void setCompiled(byte[] compiled) {
        this.compiled = compiled;
    }
}
