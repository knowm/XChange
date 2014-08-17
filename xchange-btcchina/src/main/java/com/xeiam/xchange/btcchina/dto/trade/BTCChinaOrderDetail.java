package com.xeiam.xchange.btcchina.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaOrderDetail {

  private final long dateline;
  private final BigDecimal price;
  private final BigDecimal amount;

  public BTCChinaOrderDetail(@JsonProperty("dateline") long dateline, @JsonProperty("price") String price, @JsonProperty("amount") String amount) {

    this.dateline = dateline;
    this.price = new BigDecimal(price.replaceAll(",", ""));
    this.amount = new BigDecimal(amount.replaceAll(",", ""));
  }

  public long getDateline() {

    return dateline;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

}
