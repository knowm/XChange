package org.knowm.xchange.bitfinex.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexLend {

  private final BigDecimal rate;
  private final BigDecimal amountLent;
  private final long timestamp;

  /**
   * Constructor
   *
   * @param rate
   * @param amountLent
   * @param timestamp
   */
  public BitfinexLend(@JsonProperty("rate") BigDecimal rate, @JsonProperty("amount_lent") BigDecimal amountLent,
      @JsonProperty("timestamp") long timestamp) {

    this.rate = rate;
    this.amountLent = amountLent;
    this.timestamp = timestamp;
  }

  public BigDecimal getRate() {

    return rate;
  }

  public BigDecimal getAmountLent() {

    return amountLent;
  }

  public long getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "BitfinexLend [rate=" + rate + ", amountLent=" + amountLent + ", timestamp=" + timestamp + "]";
  }

}
