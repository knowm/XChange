package org.knowm.xchange.btcchina.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaOrderDetail {

  private final long dateline;
  private final BigDecimal price;
  private final BigDecimal amount;

  public BTCChinaOrderDetail(@JsonProperty("dateline") long dateline, @JsonProperty("price") String price, @JsonProperty("amount") String amount) {

    this.dateline = dateline;
    this.price = new BigDecimal(price.replace(",", ""));
    this.amount = new BigDecimal(amount.replace(",", ""));
  }

  /**
   * Returns the time of this trade.
   *
   * @return Unix time in seconds since 1 January 1970.
   */
  public long getDateline() {

    return dateline;
  }

  /**
   * Returns the price of this trade.
   *
   * @return Price for 1 BTC/LTC.
   */
  public BigDecimal getPrice() {

    return price;
  }

  /**
   * Returns the amount filled in this trade.
   *
   * @return Amount filled in this trade.
   */
  public BigDecimal getAmount() {

    return amount;
  }

}
