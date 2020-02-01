package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbaseProCurrency {

  private final BigDecimal maxPrecision;
  private final String name;
  private final String minSize;
  private final CoinbaseProCurrencyDetails details;
  private final String id;
  private final Object message;
  private final String status;

  public CoinbaseProCurrency(
      @JsonProperty("max_precision") BigDecimal maxPrecision,
      @JsonProperty("name") String name,
      @JsonProperty("min_size") String minSize,
      @JsonProperty("details") CoinbaseProCurrencyDetails details,
      @JsonProperty("id") String id,
      @JsonProperty("message") Object message,
      @JsonProperty("status") String status) {
    this.maxPrecision = maxPrecision;
    this.name = name;
    this.minSize = minSize;
    this.details = details;
    this.id = id;
    this.message = message;
    this.status = status;
  }

  public BigDecimal getMaxPrecision() {
    return maxPrecision;
  }

  public String getName() {
    return name;
  }

  public String getMinSize() {
    return minSize;
  }

  public CoinbaseProCurrencyDetails getDetails() {
    return details;
  }

  public String getId() {
    return id;
  }

  public Object getMessage() {
    return message;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "CoinbaseProCurrency{"
        + "max_precision = '"
        + maxPrecision
        + '\''
        + ",name = '"
        + name
        + '\''
        + ",min_size = '"
        + minSize
        + '\''
        + ",details = '"
        + details
        + '\''
        + ",id = '"
        + id
        + '\''
        + ",message = '"
        + message
        + '\''
        + ",status = '"
        + status
        + '\''
        + "}";
  }
}
