package org.knowm.xchange.paymium.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class PaymiumMarketOrder {

  private final String currency;
  private final long timestamp;
  private final BigDecimal price;
  private final BigDecimal amount;

  public PaymiumMarketOrder(
      @JsonProperty("currency") String currency,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount) {

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

    return "PaymiumMarketOrder{"
        + "currency='"
        + currency
        + '\''
        + ", timestamp="
        + timestamp
        + ", price="
        + price
        + ", amount="
        + amount
        + '}';
  }
}
