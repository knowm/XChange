package org.knowm.xchange.bitfinex.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitfinexLendLevel {

  private final BigDecimal rate;
  private final BigDecimal amount;
  private final int period;
  private final float timestamp;
  private final String frr;

  /**
   * Constructor
   *
   * @param rate
   * @param amount
   * @param period
   * @param timestamp
   * @param frr
   */
  public BitfinexLendLevel(
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("period") int period,
      @JsonProperty("timestamp") float timestamp,
      @JsonProperty("frr") String frr) {

    this.rate = rate;
    this.amount = amount;
    this.period = period;
    this.timestamp = timestamp;
    this.frr = frr;
  }

  public BigDecimal getRate() {

    return rate;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public int getPeriod() {

    return period;
  }

  public float getTimestamp() {

    return timestamp;
  }

  public String getFrr() {

    return frr;
  }

  @Override
  public String toString() {

    return "BitfinexLendLevel [rate="
        + rate
        + ", amount="
        + amount
        + ", period="
        + period
        + ", timestamp="
        + timestamp
        + ", frr="
        + frr
        + "]";
  }
}
