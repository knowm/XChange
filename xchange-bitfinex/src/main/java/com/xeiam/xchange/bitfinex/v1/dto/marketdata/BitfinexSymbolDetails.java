package com.xeiam.xchange.bitfinex.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BitfinexSymbolDetails {

  private final String pair;
  private final int pricePrecision;
  private final BigDecimal initialMargin;
  private final BigDecimal minimumMargin;
  private final BigDecimal maximumOrderSize;
  private final BigDecimal minimumOrderSize;


  public BitfinexSymbolDetails(@JsonProperty("pair") String pair,
                               @JsonProperty("price_precision") int pricePrecision,
                               @JsonProperty("initial_margin") BigDecimal initialMargin,
                               @JsonProperty("minimum_margin") BigDecimal minimumMargin,
                               @JsonProperty("maximum_order_size") BigDecimal maximumOrderSize,
                               @JsonProperty("minimum_order_size") BigDecimal minimumOrderSize) {
    this.pair = pair;
    this.pricePrecision = pricePrecision;
    this.initialMargin = initialMargin;
    this.minimumMargin = minimumMargin;
    this.maximumOrderSize = maximumOrderSize;
    this.minimumOrderSize = minimumOrderSize;
  }

  public String getPair() {
    return pair;
  }

  public int getPricePrecision() {
    return pricePrecision;
  }

  public BigDecimal getInitialMargin() {
    return initialMargin;
  }

  public BigDecimal getMinimumMargin() {
    return minimumMargin;
  }

  public BigDecimal getMaximumOrderSize() {
    return maximumOrderSize;
  }

  public BigDecimal getMinimumOrderSize() {
    return minimumOrderSize;
  }
}
