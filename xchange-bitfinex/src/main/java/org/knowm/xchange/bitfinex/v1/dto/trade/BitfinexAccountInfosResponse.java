package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitfinexAccountInfosResponse {

  private final BigDecimal makerFees;
  private final BigDecimal takerFees;
  private final BitfinexFee[] fees;

  public BitfinexAccountInfosResponse(
      @JsonProperty("maker_fees") BigDecimal makerFees,
      @JsonProperty("taker_fees") BigDecimal takerFees,
      @JsonProperty("fees") BitfinexFee[] fees) {
    this.makerFees = makerFees;
    this.takerFees = takerFees;
    this.fees = fees;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("BitfinexAccountInfosResponse [makerFees=");
    builder.append(makerFees);
    builder.append(", takerFees=");
    builder.append(takerFees);
    builder.append("]");
    return builder.toString();
  }

  public BigDecimal getMakerFees() {
    return makerFees;
  }

  public BigDecimal getTakerFees() {
    return takerFees;
  }

  public BitfinexFee[] getFees() {
    return fees;
  }
}
