package com.xeiam.xchange.bitbay.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 16/12/14
 * Time: 14:36
 */
public class BitbayMarketAll extends BitbayTicker {


    private BigDecimal[][] asks;
    private BigDecimal[][] bids;
    private BitbayTrade[] trades;

    public BitbayMarketAll(@JsonProperty("max") BigDecimal max,
                           @JsonProperty("min") BigDecimal min,
                           @JsonProperty("last") BigDecimal last,
                           @JsonProperty("bid") BigDecimal bid,
                           @JsonProperty("ask") BigDecimal ask,
                           @JsonProperty("vwap") BigDecimal vwap,
                           @JsonProperty("average") BigDecimal average,
                           @JsonProperty("volume") BigDecimal volume,
                           @JsonProperty("asks") BigDecimal[][] asks,
                           @JsonProperty("bids") BigDecimal[][] bids,
                           @JsonProperty("transactions") BitbayTrade[] trades) {
        super(max, min, last, bid, ask, vwap, average, volume);

        this.asks = asks;
        this.bids = bids;
        this.trades = trades;
    }

    public BigDecimal[][] getAsks() {
        return asks;
    }

    public BigDecimal[][] getBids() {
        return bids;
    }

    public BitbayTrade[] getTrades() {
        return trades;
    }
}
