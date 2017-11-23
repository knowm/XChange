package org.knowm.xchange.dsx.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public final class DSXBar {

  private final BigDecimal high;
  private final BigDecimal open;
  private final BigDecimal low;
  private final BigDecimal close;
  private final BigDecimal amount;
  private final long timestamp;

  public DSXBar(@JsonProperty("high") BigDecimal high, @JsonProperty("open") BigDecimal open,
      @JsonProperty("low") BigDecimal low, @JsonProperty("close") BigDecimal close,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("timestamp") long timestamp) {

    this.high = high;
    this.open = open;
    this.low = low;
    this.close = close;
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getOpen() {

    return open;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getClose() {

    return close;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "DSXBar{" +
        "high=" + high +
        ", open=" + open +
        ", low=" + low +
        ", close=" + close +
        ", amount=" + amount +
        ", timestamp=" + timestamp +
        '}';
  }

}
