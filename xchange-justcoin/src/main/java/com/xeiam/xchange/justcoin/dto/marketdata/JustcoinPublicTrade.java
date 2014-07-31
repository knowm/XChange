package com.xeiam.xchange.justcoin.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class JustcoinPublicTrade {

  private final String tid;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final long date;

  public JustcoinPublicTrade(@JsonProperty("tid") String tid, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount, @JsonProperty("date") long date) {

    this.tid = tid;
    this.price = price;
    this.amount = amount;
    this.date = date;
  }

  public String getTid() {

    return tid;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  @Override
  public String toString() {

    return "JustcoinPublicTrade [tid=" + tid + ", price=" + price + ", amount=" + amount + ", date=" + date + "]";
  }

}
