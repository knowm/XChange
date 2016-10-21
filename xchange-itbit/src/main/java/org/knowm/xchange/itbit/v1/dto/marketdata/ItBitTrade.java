package org.knowm.xchange.itbit.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItBitTrade {

  private final BigDecimal amount;
  private final String timestamp;
  private final BigDecimal price;
  private final long tid;

  public ItBitTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("timestamp") String timestamp, @JsonProperty("price") BigDecimal price,
      @JsonProperty("tid") long tid) {

    this.amount = amount;
    this.timestamp = timestamp;
    this.price = price;
    this.tid = tid;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getTimestamp() {

    return timestamp;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public long getTid() {

    return tid;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ItBitTrade [amount=");
    builder.append(amount);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", price=");
    builder.append(price);
    builder.append(", tid=");
    builder.append(tid);
    builder.append("]");
    return builder.toString();
  }
}
