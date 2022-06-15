package org.knowm.xchange.okex.v5.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OkexTicker {

    @JsonProperty("instType")
    private String instrumentType;

    @JsonProperty("instId")
    private String instrumentId;

    @JsonProperty("last")
    private String last;

    /**
     * 最新成交的数量
     */
    @JsonProperty("lastSz")
    private String lastSz;

    @JsonProperty("askPx")
    private String askPx;

    @JsonProperty("askSz")
    private String askSz;

    @JsonProperty("bidPx")
    private String bidPx;

    @JsonProperty("bidSz")
    private String bidSz;

    @JsonProperty("open24h")
    private String open24h;

    @JsonProperty("high24h")
    private String high24h;

    @JsonProperty("low24h")
    private String low24h;

    /**
     * 数值为计价货币的数量。
     */
    @JsonProperty("volCcy24h")
    private String volCcy24h;

    /**
     * 数值为交易货币的数量。
     */
    @JsonProperty("vol24h")
    private String vol24h;

    /**
     * UTC 0 时开盘价
     */
    @JsonProperty("sodUtc0")
    private String sodUtc0;

    /**
     * sodUtc8
     */
    @JsonProperty("sodUtc8")
    private String sodUtc8;

    @JsonProperty("ts")
    private String ts;
}
