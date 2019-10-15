package org.knowm.xchange.blockchainpit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PitRequest {
    private  final String action;
    private final String channel;

    public PitRequest(
        @JsonProperty("action") String action,
        @JsonProperty("channel") String channel
    ) {
        this.action = action;
        this.channel = channel;
    }

    public String getAction() {
        return action;
    }

    public String getChannel() {
        return channel;
    }
}
