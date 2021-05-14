package org.knowm.xchange.bitso.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author Piotr Ładyżyński */
public class BitsoTransaction {

  private final long date;
  private final int tid;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String side;

  /**
   * Constructor
   *
   * @param date Unix timestamp date and time
   * @param tid Transaction id
   * @param price BTC price
   * @param amount BTC amount
   * @param side "buy"/"sell" for the type of order the trade has completed
   */
  public BitsoTransaction(
      @JsonProperty("date") long date,
      @JsonProperty("tid") int tid,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("side") String side) {

    this.date = date;
    this.tid = tid;
    this.price = price;
    this.amount = amount;
    this.side = side;
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

  public String getSide() {

    return side;
  }

  public BigDecimal calculateFeeBtc() {

    return roundUp(amount.multiply(new BigDecimal(.5))).divide(new BigDecimal(100.));
  }

  private BigDecimal roundUp(BigDecimal x) {

    long n = x.longValue();
    return new BigDecimal(x.equals(new BigDecimal(n)) ? n : n + 1);
  }

  public BigDecimal calculateFeeMxn() {

    return calculateFeeBtc().multiply(price);
  }

  @Override
  public String toString() {

    return "Transaction [date="
        + date
        + ", tid="
        + tid
        + ", price="
        + price
        + ", amount="
        + amount
        + ", side="
        + side
        + "]";
  }
}
