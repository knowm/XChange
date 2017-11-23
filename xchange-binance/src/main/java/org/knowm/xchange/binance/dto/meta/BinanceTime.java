package org.knowm.xchange.binance.dto.meta;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BinanceTime {

    @JsonProperty
    private long serverTime;

    public Date getServerTime() {
        return new Date(serverTime);
    }
}
