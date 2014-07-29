package com.xeiam.xchange.btcchina.dto.trade;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

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

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return ToStringBuilder.reflectionToString(this);
  }

}
