package com.xeiam.xchange.hitbtc.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class HitbtcTrade {

  private final long date;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String tid;

  /**
   * Constructor
   * 
   * @param date
   * @param price
   * @param amount
   * @param tid
   */
  public HitbtcTrade(@JsonProperty("date") long date, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("tid") String tid) {

    this.date = date;
    this.price = price;
    this.amount = amount;
    this.tid = tid;
  }

  public long getDate() {

    return date;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getTid() {

    return tid;
  }

  @Override
  public String toString() {

    return "HitbtcTrade{" + "date=" + date + ", price=" + price + ", amount=" + amount + ", tid='" + tid + '\'' + '}';
  }
}
