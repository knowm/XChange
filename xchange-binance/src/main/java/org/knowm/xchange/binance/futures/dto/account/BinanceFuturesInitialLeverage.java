package org.knowm.xchange.binance.futures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public final class BinanceFuturesInitialLeverage {
    public final int leverage;
    public final BigDecimal maxNotionalValue;
    public final String symbol;

    public BinanceFuturesInitialLeverage(
            @JsonProperty("leverage") int leverage,
            @JsonProperty("maxNotionalValue") BigDecimal maxNotionalValue,
            @JsonProperty("symbol") String symbol) {
        this.leverage = leverage;
        this.maxNotionalValue = maxNotionalValue;
        this.symbol = symbol;
    }
}
