package org.knowm.xchange.ftx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class FtxMarketDto {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("baseCurrency")
    private final String baseCurrency;

    @JsonProperty("quoteCurrency")
    private final String quoteCurrency;

    @JsonProperty("type")
    private final String type;

    @JsonProperty("underlying")
    private final String underlying;

    @JsonProperty("enabled")
    private final boolean enabled;

    @JsonProperty("ask")
    private final BigDecimal ask;

    @JsonProperty("bid")
    private final BigDecimal bid;

    @JsonProperty("last")
    private final BigDecimal last;

    @JsonProperty("postOnly")
    private final boolean postOnly;

    @JsonProperty("priceIncrement")
    private final BigDecimal priceIncrement;

    @JsonProperty("sizeIncrement")
    private final BigDecimal sizeIncrement;

    @JsonProperty("restricted")
    private final boolean restricted;

    @JsonCreator
    public FtxMarketDto(
        @JsonProperty("name")String name,
        @JsonProperty("baseCurrency")String baseCurrency,
        @JsonProperty("quoteCurrency") String quoteCurrency,
        @JsonProperty("type") String type,
        @JsonProperty("underlying") String underlying,
        @JsonProperty("enabled") boolean enabled,
        @JsonProperty("ask") BigDecimal ask,
        @JsonProperty("bid") BigDecimal bid,
        @JsonProperty("last") BigDecimal last,
        @JsonProperty("postOnly") boolean postOnly,
        @JsonProperty("priceIncrement") BigDecimal priceIncrement,
        @JsonProperty("sizeIncrement") BigDecimal sizeIncrement,
        @JsonProperty("restricted") boolean restricted) {
        this.name = name;
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.type = type;
        this.underlying = underlying;
        this.enabled = enabled;
        this.ask = ask;
        this.bid = bid;
        this.last = last;
        this.postOnly = postOnly;
        this.priceIncrement = priceIncrement;
        this.sizeIncrement = sizeIncrement;
        this.restricted = restricted;
    }

    public String getName() {
        return name;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public String getType() {
        return type;
    }

    public String getUnderlying() {
        return underlying;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BigDecimal getLast() {
        return last;
    }

    public boolean isPostOnly() {
        return postOnly;
    }

    public BigDecimal getPriceIncrement() {
        return priceIncrement;
    }

    public BigDecimal getSizeIncrement() {
        return sizeIncrement;
    }

    public boolean isRestricted() {
        return restricted;
    }

    @Override
    public String toString() {
        return "FtxMarketDto{" +
            "name='" + name + '\'' +
            ", baseCurrency='" + baseCurrency + '\'' +
            ", quoteCurrency='" + quoteCurrency + '\'' +
            ", type='" + type + '\'' +
            ", underlying='" + underlying + '\'' +
            ", enabled=" + enabled +
            ", ask=" + ask +
            ", bid=" + bid +
            ", last=" + last +
            ", postOnly=" + postOnly +
            ", priceIncrement=" + priceIncrement +
            ", sizeIncrement=" + sizeIncrement +
            ", restricted=" + restricted +
            '}';
    }
}
