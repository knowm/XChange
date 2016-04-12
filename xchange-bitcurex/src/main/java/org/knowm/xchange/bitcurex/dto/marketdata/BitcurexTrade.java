package org.knowm.xchange.bitcurex.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcurexTrade {

  private final BigDecimal amount;
  private final long date;
  private final BigDecimal price;
  private final long tid;
  private final long type;

  public BitcurexTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("date") long date, @JsonProperty("price") BigDecimal price,
      @JsonProperty("tid") long tid, @JsonProperty("type") long type) {

    this.amount = amount;
    this.date = date;
    this.price = price;
    this.tid = tid;
    this.type = type;
  }

  public BigDecimal getAmount() {

    return this.amount;
  }

  public long getDate() {

    return this.date;
  }

  public BigDecimal getPrice() {

    return this.price;
  }

  public long getTid() {

    return this.tid;
  }

  public long getType() {

    return this.type;
  }

  @Override
  public String toString() {

    return "BitcurexTrade [amount=" + amount + ", date=" + date + ", price=" + price + ", tid=" + tid + ", type=" + type + "]";
  }
}
