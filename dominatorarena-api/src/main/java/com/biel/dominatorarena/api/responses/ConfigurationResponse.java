package com.biel.dominatorarena.api.responses;

/**
 * Created by Biel on 4/12/2016.
 */
public class ConfigurationResponse {
    private Long serverId;
    private String name;
    private String contents;

    public ConfigurationResponse(Long serverId, String name, String contents) {
        this.serverId = serverId;
        this.name = name;
        this.contents = contents;
    }

    ConfigurationResponse() {
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
}
