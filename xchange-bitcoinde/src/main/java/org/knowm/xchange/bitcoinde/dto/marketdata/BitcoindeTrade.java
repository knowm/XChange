package org.knowm.xchange.bitcoinde.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author matthewdowney
 */
public class BitcoindeTrade {

  private final long date;
  private final BigDecimal price;
  private final String amount;
  private final long tid;

  /**
   * Constructor
   * 
   * @param tid
   * @param price
   * @param amount
   * @param date
   */
  public BitcoindeTrade(@JsonProperty("tid") long tid, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") String amount,
      @JsonProperty("date") long date) {

    this.tid = tid;
    this.price = price;
    this.amount = amount;
    this.date = date;
  }

  public long getTid() {

    return tid;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public String getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  @Override
  public String toString() {

    return "BitcoindeTrade{" + "date=" + date + ", price=" + price + ", amount='" + amount + "', date=" + date + '}';
  }
}
