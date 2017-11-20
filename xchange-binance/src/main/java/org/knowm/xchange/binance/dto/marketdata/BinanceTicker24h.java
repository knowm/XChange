package org.knowm.xchange.binance.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class BinanceTicker24h {
    
    public final BigDecimal priceChange;
    public final BigDecimal priceChangePercent;
    public final BigDecimal weightedAvgPrice;
    public final BigDecimal prevClosePrice;
    public final BigDecimal lastPrice;
    public final BigDecimal lastQty;
    public final BigDecimal bidPrice;
    public final BigDecimal bidQty;
    public final BigDecimal askPrice;
    public final BigDecimal askQty;
    public final BigDecimal openPrice;
    public final BigDecimal highPrice;
    public final BigDecimal lowPrice;
    public final BigDecimal volume;
    public final BigDecimal quoteVolume;
    public final long openTime;
    public final long closeTime;
    /** First tradeId */
    public final long firstId;
    /** Last tradeId */
    public final long lastId;
    /** Trade count */
    public final long count;
    
    
    public BinanceTicker24h(@JsonProperty("priceChange") BigDecimal priceChange
            , @JsonProperty("priceChangePercent") BigDecimal priceChangePercent
            , @JsonProperty("weightedAvgPrice") BigDecimal weightedAvgPrice
            , @JsonProperty("prevClosePrice") BigDecimal prevClosePrice
            , @JsonProperty("lastPrice") BigDecimal lastPrice
            , @JsonProperty("lastQty") BigDecimal lastQty
            , @JsonProperty("bidPrice") BigDecimal bidPrice
            , @JsonProperty("bidQty") BigDecimal bidQty
            , @JsonProperty("askPrice") BigDecimal askPrice
            , @JsonProperty("askQty") BigDecimal askQty
            , @JsonProperty("openPrice") BigDecimal openPrice
            , @JsonProperty("highPrice") BigDecimal highPrice
            , @JsonProperty("lowPrice") BigDecimal lowPrice
            , @JsonProperty("volume") BigDecimal volume
            , @JsonProperty("quoteVolume") BigDecimal quoteVolume
            , @JsonProperty("openTime") long openTime
            , @JsonProperty("closeTime") long closeTime
            , @JsonProperty("firstId") long firstId
            , @JsonProperty("lastId") long lastId
            , @JsonProperty("count") long count) {
        this.priceChange = priceChange;
        this.priceChangePercent = priceChangePercent;
        this.weightedAvgPrice = weightedAvgPrice;
        this.prevClosePrice = prevClosePrice;
        this.lastPrice = lastPrice;
        this.lastQty = lastQty;
        this.bidPrice = bidPrice;
        this.bidQty = bidQty;
        this.askPrice = askPrice;
        this.askQty = askQty;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.volume = volume;
        this.quoteVolume = quoteVolume;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.firstId = firstId;
        this.lastId = lastId;
        this.count = count;
    }

    public Date getOpenTime() {
        return new Date(openTime);
    }

    public Date getCloseTime() {
        return new Date(closeTime);
    }
}
