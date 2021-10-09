package org.knowm.xchange.binance.futures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BinanceUserCommissionRate {
    public final String symbol;
    public final BigDecimal makerCommissionRate;
    public final BigDecimal takerCommissionRate;

    public BinanceUserCommissionRate(
            @JsonProperty("symbol") String symbol,
            @JsonProperty("makerCommissionRate") BigDecimal makerCommissionRate,
            @JsonProperty("takerCommissionRate") BigDecimal takerCommissionRate) {
        this.symbol = symbol;
        this.makerCommissionRate = makerCommissionRate;
        this.takerCommissionRate = takerCommissionRate;
    }
}
