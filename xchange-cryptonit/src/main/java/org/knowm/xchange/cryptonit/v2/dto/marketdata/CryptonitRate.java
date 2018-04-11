package org.knowm.xchange.cryptonit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Data object representing Rate from Cryptonit */
public final class CryptonitRate {

  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal bid;
  private final BigDecimal ask;

  /**
   * @param high
   * @param low
   * @param bid
   * @param ask
   * @param last
   */
  public CryptonitRate(
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("last") BigDecimal last) {

    this.high = high;
    this.low = low;
    this.ask = ask;
    this.bid = bid;
    this.last = last;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  @Override
  public String toString() {

    return "CryptonitRate [last="
        + last
        + ", high="
        + high
        + ", low="
        + low
        + ", bid="
        + bid
        + ", ask="
        + ask
        + "]";
  }
}
