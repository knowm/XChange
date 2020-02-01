package org.knowm.xchange.globitex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class GlobitexTrade implements Serializable {

  @JsonProperty("date")
  private final long timestamp;

  @JsonProperty("price")
  private final BigDecimal price;

  @JsonProperty("amount")
  private final BigDecimal amount;

  @JsonProperty("tid")
  private final long tid;

  @JsonProperty("side")
  @JsonIgnore
  private final String side;

  public GlobitexTrade(
      @JsonProperty("date") long timestamp,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("tid") long tid,
      @JsonProperty("side") String side) {
    this.timestamp = timestamp;
    this.price = price;
    this.amount = amount;
    this.tid = tid;
    this.side = side;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public long getTid() {
    return tid;
  }

  public String getSide() {
    return side;
  }

  @Override
  public String toString() {
    return "GlobitexTrade{"
        + "date="
        + timestamp
        + ", price="
        + price
        + ", amount="
        + amount
        + ", tid='"
        + tid
        + '\''
        + ", side='"
        + side
        + '\''
        + '}';
  }
}
