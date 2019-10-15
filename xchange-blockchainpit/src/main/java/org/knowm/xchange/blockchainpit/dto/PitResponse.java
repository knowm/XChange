package org.knowm.xchange.blockchainpit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PitResponse {
    private final Long seqnum;
    private final String event;
    private final String channel;

    public PitResponse(
        @JsonProperty("seqnum") Long seqnum,
        @JsonProperty("event") String event,
        @JsonProperty("channel") String channel
    ) {

        this.seqnum = seqnum;
        this.event = event;
        this.channel = channel;
    }

    public Long getSeqnum() {
        return seqnum;
    }

    public String getEvent() {
        return event;
    }

    public String getChannel() {
        return channel;
    }
}
