package org.knowm.xchange.btcchina.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaMarketDepthOrder {

  private final BigDecimal price;
  private final BigDecimal amount;

  public BTCChinaMarketDepthOrder(@JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount) {

    this.price = price;
    this.amount = amount;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return "BTCChinaMarketDepthOrder [price=" + price + ", amount=" + amount + "]";
  }

}
