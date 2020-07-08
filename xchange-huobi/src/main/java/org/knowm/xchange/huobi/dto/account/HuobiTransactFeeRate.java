package org.knowm.xchange.huobi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class HuobiTransactFeeRate {

  private final String symbol;
  private final BigDecimal makerFeeRate;
  private final BigDecimal takerFeeRate;
  private final BigDecimal actualMakerRate;
  private final BigDecimal actualTakerRate;

  public HuobiTransactFeeRate(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("makerFeeRate") BigDecimal makerFeeRate,
      @JsonProperty("takerFeeRate") BigDecimal takerFeeRate,
      @JsonProperty("actualMakerRate") BigDecimal actualMakerRate,
      @JsonProperty("actualTakerRate") BigDecimal actualTakerRate) {
    this.symbol = symbol;
    this.makerFeeRate = makerFeeRate;
    this.takerFeeRate = takerFeeRate;
    this.actualMakerRate = actualMakerRate;
    this.actualTakerRate = actualTakerRate;
  }



  @Override
  public String toString() {
    return String.format(
        "[symbol = %s, makerFeeRate = %f, takerFeeRate = %f, actualMakerRate = %f, actualTakerRate = %f]",
            getSymbol(), getMakerFeeRate(), getTakerFeeRate(), getActualMakerRate(), getActualTakerRate());
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getMakerFeeRate() {
    return makerFeeRate;
  }

  public BigDecimal getTakerFeeRate() {
    return takerFeeRate;
  }

  public BigDecimal getActualMakerRate() {
    return actualMakerRate;
  }

  public BigDecimal getActualTakerRate() {
    return actualTakerRate;
  }

}
