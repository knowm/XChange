package org.knowm.xchange.btc38.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author nxtpool
 */
public class Btc38Trade {

  private final long date;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String tid;
  private final String type;

  /**
   * Constructor
   *
   * @param date
   * @param price
   * @param amount
   * @param tid
   */
  public Btc38Trade(@JsonProperty("date") long date, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("tid") String tid,
      @JsonProperty("type") String type) {

    this.date = date;
    this.price = price;
    this.amount = amount;
    this.tid = tid;
    this.type = type;
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

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Btc38Trade{" + "date=" + date + ", , type=" + type + ", price=" + price + ", amount=" + amount + ", tid='" + tid + '\'' + '}';
  }
}