package org.knowm.xchange.coinbasepro.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbaseProFee {
  private final BigDecimal makerRate;
  private final BigDecimal takerRate;

  public CoinbaseProFee(
      @JsonProperty("maker_fee_rate") BigDecimal makerRate,
      @JsonProperty("taker_fee_rate") BigDecimal takerRate) {
    this.makerRate = makerRate;
    this.takerRate = takerRate;
  }

  public BigDecimal getMakerRate() {
    return makerRate;
  }

  public BigDecimal getTakerRate() {
    return takerRate;
  }
}
