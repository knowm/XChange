package org.knowm.xchange.gdax.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class GDAXSendMoneyRequest {

  @JsonProperty("type")
  private final String type = "send";

  @JsonProperty("to")
  private final String to;

  @JsonProperty("amount")
  private final BigDecimal amount;

  @JsonProperty("currency")
  private final String currency;

  public GDAXSendMoneyRequest(String to, BigDecimal amount, String currency) {
    this.to = to;
    this.amount = amount;
    this.currency = currency;
  }

  public String getType() {
    return type;
  }

  public String getTo() {
    return to;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  @Override
  public String toString() {
    return "CoinbaseExSendMoneyRequest{"
        + "type='"
        + type
        + '\''
        + ", to='"
        + to
        + '\''
        + ", amount="
        + amount
        + ", currency='"
        + currency
        + '\''
        + '}';
  }
}
