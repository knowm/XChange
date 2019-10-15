package org.knowm.xchange.blockchainpit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PitHeartbeat extends PitResponse {
    private final String timestamp;

    public PitHeartbeat(
            @JsonProperty("seqnum") Long seqnum,
            @JsonProperty("event") String event,
            @JsonProperty("channel") String channel,
            @JsonProperty("timestamp") String timestamp
    ) {
        super(seqnum, event, channel);
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
