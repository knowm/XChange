package org.knowm.xchange.btcchina.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from BTCChina
 * </p>
 */
public final class BTCChinaTrade {

  private final BigDecimal amount;
  private final long date;
  private final BigDecimal price;
  private final long tid;
  private final String orderType;

  /**
   * Constructor
   *
   * @param amount
   * @param date
   * @param price
   * @param tid
   * @param orderType
   */
  public BTCChinaTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("date") long date, @JsonProperty("price") BigDecimal price,
      @JsonProperty("tid") long tid, @JsonProperty("type") String orderType) {

    this.amount = amount;
    this.date = date;
    this.price = price;
    this.tid = tid;
    this.orderType = orderType;
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

  public String getOrderType() {

    return this.orderType;
  }

  @Override
  public String toString() {

    return "BTCChinaTrades [amount=" + amount + ", date=" + date + ", price=" + price + ", tid=" + tid + ", type=" + orderType + "]";
  }

}
