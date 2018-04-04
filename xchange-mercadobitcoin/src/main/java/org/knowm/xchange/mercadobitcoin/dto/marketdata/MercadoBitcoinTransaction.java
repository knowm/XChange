package org.knowm.xchange.mercadobitcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * @author Matija Mazi
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinTransaction {

  private final long date;
  private final long tid;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String type;

  /**
   * Constructor
   *
   * @param date Unix timestamp date and time
   * @param tid Transaction id
   * @param price BTC price in BRL
   * @param amount BTC amount
   * @param type buy or sell
   */
  public MercadoBitcoinTransaction(
      @JsonProperty("date") long date,
      @JsonProperty("tid") long tid,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("type") String type) {

    this.date = date;
    this.tid = tid;
    this.price = price;
    this.amount = amount;
    this.type = type;
  }

  @Override
  public String toString() {

    return "MercadoBitcoinTransaction ["
        + "date="
        + date
        + ", tid="
        + tid
        + ", price="
        + price
        + ", amount="
        + amount
        + ", type='"
        + type
        + '\''
        + ']';
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

  public String getType() {

    return type;
  }
}
