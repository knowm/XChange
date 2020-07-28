package org.knowm.xchange.huobi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class HuobiFeeRate {

  private final String symbol;
  private final BigDecimal makerFee;
  private final BigDecimal takerFee;

  public HuobiFeeRate(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("maker-fee") BigDecimal makerFee,
      @JsonProperty("taker-fee") BigDecimal takerFee) {
    this.symbol = symbol;
    this.makerFee = makerFee;
    this.takerFee = takerFee;
  }



  @Override
  public String toString() {
    return String.format(
        "[symbol = %s, makerFee = %f, takerFee = %f]",
            getSymbol(), getMakerFee(), getTakerFee());
  }

  public String getSymbol() {
    return symbol;
  }


  public BigDecimal getMakerFee() {
    return makerFee;
  }

  public BigDecimal getTakerFee() {
    return takerFee;
  }

}
