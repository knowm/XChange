package org.knowm.xchange.btctrade.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BTCTradeTicker {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal buy;
  private final BigDecimal sell;
  private final BigDecimal last;
  private final BigDecimal vol;

  public BTCTradeTicker(
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("buy") BigDecimal buy,
      @JsonProperty("sell") BigDecimal sell,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("vol") BigDecimal vol) {

    this.high = high;
    this.low = low;
    this.buy = buy;
    this.sell = sell;
    this.last = last;
    this.vol = vol;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getBuy() {

    return buy;
  }

  public BigDecimal getSell() {

    return sell;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getVol() {

    return vol;
  }
}
