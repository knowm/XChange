package org.knowm.xchange.binance.futures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BinanceFuturesLeverageBracket {

    public final int bracket;
    public final int initialLeverage;
    public final BigDecimal notionalCap;
    public final BigDecimal notionalFloor;
    public final BigDecimal maintMarginRatio;
    public final BigDecimal cum;

    public BinanceFuturesLeverageBracket(
            @JsonProperty("leverage") int bracket,
            @JsonProperty("initialLeverage") int initialLeverage,
            @JsonProperty("notionalCap") BigDecimal notionalCap,
            @JsonProperty("notionalFloor") BigDecimal notionalFloor,
            @JsonProperty("maintMarginRatio") BigDecimal maintMarginRatio,
            @JsonProperty("cum") BigDecimal cum) {
        this.bracket = bracket;
        this.initialLeverage = initialLeverage;
        this.notionalCap = notionalCap;
        this.notionalFloor = notionalFloor;
        this.maintMarginRatio = maintMarginRatio;
        this.cum = cum;
    }
}

