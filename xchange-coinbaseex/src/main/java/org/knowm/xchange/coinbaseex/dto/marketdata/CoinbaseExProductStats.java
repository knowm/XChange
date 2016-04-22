package org.knowm.xchange.coinbaseex.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yingzhe on 4/4/2015.
 */
public class CoinbaseExProductStats {
  private final BigDecimal open;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;

  public CoinbaseExProductStats(@JsonProperty("open") BigDecimal open, @JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low,
      @JsonProperty("volume") BigDecimal volume) {
    this.open = open;
    this.high = high;
    this.low = low;
    this.volume = volume;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getVolume() {
    return volume;
  }
}
