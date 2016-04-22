package org.knowm.xchange.huobi.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiTickerObject {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal last;
  private final BigDecimal vol;
  private final BigDecimal buy;
  private final BigDecimal sell;

  public HuobiTickerObject(@JsonProperty("high") final BigDecimal high, @JsonProperty("low") final BigDecimal low,
      @JsonProperty("last") final BigDecimal last, @JsonProperty("vol") final BigDecimal vol, @JsonProperty("buy") final BigDecimal buy,
      @JsonProperty("sell") final BigDecimal sell) {

    this.high = high;
    this.low = low;
    this.last = last;
    this.vol = vol;
    this.buy = buy;
    this.sell = sell;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getVol() {

    return vol;
  }

  public BigDecimal getBuy() {

    return buy;
  }

  public BigDecimal getSell() {

    return sell;
  }

}
