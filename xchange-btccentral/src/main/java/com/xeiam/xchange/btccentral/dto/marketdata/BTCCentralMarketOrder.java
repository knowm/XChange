package com.xeiam.xchange.btccentral.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class BTCCentralMarketOrder {

  private final String currency;
  private final long timestamp;
  private final BigDecimal price;
  private final BigDecimal amount;

  public BTCCentralMarketOrder(@JsonProperty("currency") String currency, @JsonProperty("timestamp") long timestamp, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount) {

    this.currency = currency;
    this.timestamp = timestamp;
    this.price = price;
    this.amount = amount;
  }

  public String getCurrency() {

    return currency;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return "BTCCentralMarketOrder{" + "currency='" + currency + '\'' + ", timestamp=" + timestamp + ", price=" + price + ", amount=" + amount + '}';
  }
}
