package org.known.xchange.acx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class AcxTicker {
  /** Current sell price */
  public final BigDecimal buy;
  /** Current sell price */
  public final BigDecimal sell;

  public final BigDecimal open;
  /** Lowest price in last 24 hours */
  public final BigDecimal low;
  /** Highest price in last 24 hours */
  public final BigDecimal high;
  /** Last price */
  public final BigDecimal last;
  /** Trade volume in last 24 hours */
  public final BigDecimal vol;

  public AcxTicker(
      @JsonProperty("buy") BigDecimal buy,
      @JsonProperty("sell") BigDecimal sell,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("vol") BigDecimal vol) {
    this.buy = buy;
    this.sell = sell;
    this.open = open;
    this.low = low;
    this.high = high;
    this.last = last;
    this.vol = vol;
  }
}
