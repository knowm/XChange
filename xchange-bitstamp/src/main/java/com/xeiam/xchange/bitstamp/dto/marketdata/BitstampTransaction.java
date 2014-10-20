package com.xeiam.xchange.bitstamp.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public class BitstampTransaction {

  private final long date;
  private final int tid;
  private final BigDecimal price;
  private final BigDecimal amount;

  /**
   * Constructor
   * 
   * @param date Unix timestamp date and time
   * @param tid Transaction id
   * @param price BTC price
   * @param amount BTC amount
   */
  public BitstampTransaction(@JsonProperty("date") long date, @JsonProperty("tid") int tid, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount) {

    this.date = date;
    this.tid = tid;
    this.price = price;
    this.amount = amount;
  }

  public int getTid() {

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

  public BigDecimal calculateFeeBtc() {

    return roundUp(amount.multiply(new BigDecimal(.5))).divide(new BigDecimal(100.));
  }

  private BigDecimal roundUp(BigDecimal x) {

    long n = x.longValue();
    return new BigDecimal(x.equals(new BigDecimal(n)) ? n : n + 1);
  }

  public BigDecimal calculateFeeUsd() {

    return calculateFeeBtc().multiply(price);
  }

  @Override
  public String toString() {

    return "Transaction [date=" + date + ", tid=" + tid + ", price=" + price + ", amount=" + amount + "]";
  }

}
