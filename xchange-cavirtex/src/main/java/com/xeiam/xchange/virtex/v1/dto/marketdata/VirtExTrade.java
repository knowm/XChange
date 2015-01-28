package com.xeiam.xchange.virtex.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from VirtEx
 * </p>
 */

@Deprecated
public final class VirtExTrade {

  private final BigDecimal amount;
  private final double date;
  private final BigDecimal price;
  private final long tid;

  /**
   * Constructor
   * 
   * @param amount
   * @param date
   * @param price
   * @param tid
   */
  public VirtExTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("date") double date, @JsonProperty("price") BigDecimal price,
      @JsonProperty("tid") long tid) {

    this.amount = amount;
    this.date = date;
    this.price = price;
    this.tid = tid;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public double getDate() {

    return date;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public long getTid() {

    return tid;
  }

  @Override
  public String toString() {

    return "VirtExTrades [amount=" + amount + ", date=" + date + ", price=" + price + ", tid=" + tid + "]";
  }

}
