package org.knowm.xchange.gemini.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class GeminiBalancesResponse {

  private final String type;
  private final String currency;
  private final BigDecimal amount;
  private final BigDecimal available;

  /**
   * Constructor
   *
   * @param type
   * @param currency
   * @param amount
   * @param available
   */
  public GeminiBalancesResponse(
      @JsonProperty("type") String type,
      @JsonProperty("currency") String currency,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("available") BigDecimal available) {

    this.type = type;
    this.currency = currency;
    this.amount = amount;
    this.available = available;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getAvailable() {

    return available;
  }

  public String getCurrency() {

    return currency;
  }

  public String getType() {

    return type;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("GeminiBalancesResponse [type=");
    builder.append(type);
    builder.append(", currency=");
    builder.append(currency);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", available=");
    builder.append(available);
    builder.append("]");
    return builder.toString();
  }
}
