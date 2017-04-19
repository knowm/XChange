package org.knowm.xchange.btce.v3.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: okhomenko
 * <p>
 * Data object representing single Trade from BTCE API v.3
 */
public class BTCETrade {

  private final BigDecimal amount;
  private final long date;
  private final BigDecimal price;
  private final long tid;
  private String tradeType;

  /**
   * Constructor
   *
   * @param tradeType
   * @param price
   * @param amount
   * @param tid
   * @param date
   */
  public BTCETrade(@JsonProperty("type") String tradeType, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("tid") long tid, @JsonProperty("timestamp") long date) {

    this.tradeType = tradeType;
    this.price = price;
    this.amount = amount;
    this.tid = tid;
    this.date = date;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public long getTid() {

    return tid;
  }

  public String getTradeType() {

    return tradeType;
  }

  @Override
  public String toString() {

    return "BTCETrade [amount=" + amount + ", timestamp=" + date + ", price=" + price + ", tid=" + tid + ", type=" + tradeType + "]";
  }

}
