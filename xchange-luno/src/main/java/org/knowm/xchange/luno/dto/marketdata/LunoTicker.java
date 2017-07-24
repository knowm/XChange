package org.knowm.xchange.luno.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoTicker {

    public final long timestamp;
    public final BigDecimal bid;
    public final BigDecimal ask;
    public final BigDecimal lastTrade;
    public final BigDecimal rolling24HourVolume;
    public final String pair;

    public LunoTicker(@JsonProperty(value="timestamp", required=true) long timestamp, @JsonProperty("bid") BigDecimal bid
            , @JsonProperty("ask") BigDecimal ask, @JsonProperty("last_trade") BigDecimal lastTrade
            , @JsonProperty("rolling_24_hour_volume") BigDecimal rolling24HourVolume, @JsonProperty("pair") String pair) {
        super();
        this.timestamp = timestamp;
        this.bid = bid;
        this.ask = ask;
        this.lastTrade = lastTrade;
        this.rolling24HourVolume = rolling24HourVolume;
        this.pair = pair;
    }

    public Date getTimestamp() {
        return new Date(timestamp);
    }

    @Override
    public String toString() {
        return "LunoTicker [timestamp=" + getTimestamp() + ", bid=" + bid + ", ask=" + ask + ", lastTrade=" + lastTrade
                + ", rolling24HourVolume=" + rolling24HourVolume + ", pair=" + pair + "]";
    }
}
