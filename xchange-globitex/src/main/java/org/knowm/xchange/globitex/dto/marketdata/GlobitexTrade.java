package org.knowm.xchange.globitex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

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

  public Date getTimestamp() {
    return new Date(new Timestamp(timestamp).getTime());
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
