package org.knowm.xchange.blockchainpit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PitSubscribe extends PitRequest {

    public PitSubscribe(
            @JsonProperty("channel") String channel
    ) {
        super("subscribe", channel);
    }
}
