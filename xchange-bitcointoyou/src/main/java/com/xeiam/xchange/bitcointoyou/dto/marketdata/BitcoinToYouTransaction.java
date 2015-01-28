package com.xeiam.xchange.bitcointoyou.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouTransaction {

  private final long date;
  private final long tid;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String type;
  private final String currency;

  public BitcoinToYouTransaction(@JsonProperty("date") long date, @JsonProperty("tid") long tid, @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("type") String type, @JsonProperty("currency") String currency) {

    this.date = date;
    this.tid = tid;
    this.price = price;
    this.amount = amount;
    this.type = type;
    this.currency = currency;
  }

  @Override
  public String toString() {

    return "BitcoinToYouTransaction [" + "date=" + date + ", tid=" + tid + ", price=" + price + ", amount=" + amount + ", type='" + type + '\''
        + ", currency='" + currency + '\'' + ']';
  }

  public long getDate() {

    return date;
  }

  public long getTid() {

    return tid;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getType() {

    return type;
  }

  public String getCurrency() {

    return currency;
  }
}
