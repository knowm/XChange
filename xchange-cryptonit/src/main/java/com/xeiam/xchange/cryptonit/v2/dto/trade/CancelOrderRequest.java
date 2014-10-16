package com.xeiam.xchange.cryptonit.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yar.kh on 03/10/14.
 */
public class CancelOrderRequest {

    String id;
    String action;
    Long timestamp;

    public CancelOrderRequest(@JsonProperty("id") String id,
                              @JsonProperty("action") String action,
                              @JsonProperty("timestamp") Long timestamp) {
        this.id = id;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
