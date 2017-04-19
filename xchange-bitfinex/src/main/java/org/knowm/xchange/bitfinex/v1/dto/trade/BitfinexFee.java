package org.knowm.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexFee {
  private final String pairs;
  private final BigDecimal makerFees;
  private final BigDecimal takerFees;

  public BitfinexFee(@JsonProperty("pairs") String pairs, @JsonProperty("maker_fees") BigDecimal makerFees,
      @JsonProperty("taker_fees") BigDecimal takerFees) {
    this.pairs = pairs;
    this.makerFees = makerFees;
    this.takerFees = takerFees;
  }

  public String getPairs() {
    return pairs;
  }

  public BigDecimal getMakerFees() {
    return makerFees;
  }

  public BigDecimal getTakerFees() {
    return takerFees;
  }
}
