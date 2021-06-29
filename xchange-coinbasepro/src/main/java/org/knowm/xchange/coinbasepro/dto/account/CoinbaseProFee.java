package org.knowm.xchange.coinbasepro.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbaseProFee {
  private final BigDecimal makerRate;
  private final BigDecimal takerRate;
  private final BigDecimal usdVolume;

  public CoinbaseProFee(
      @JsonProperty("maker_fee_rate") BigDecimal makerRate,
      @JsonProperty("taker_fee_rate") BigDecimal takerRate,
      @JsonProperty("usd_volume") BigDecimal usdVolume) {
    this.makerRate = makerRate;
    this.takerRate = takerRate;
    this.usdVolume = usdVolume;
  }

  public BigDecimal getMakerRate() {
    return makerRate;
  }

  public BigDecimal getTakerRate() {
    return takerRate;
  }

  public BigDecimal getUsdVolume() {
    return usdVolume;
  }
}
