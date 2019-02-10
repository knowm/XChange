package org.knowm.xchange.bithumb.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

public class BithumbOrderbookAll {

  private final long timestamp;
  private final String paymentCurrency;

  @JsonIgnore
  private Map<String, BithumbOrderbook> additionalProperties =
      new HashMap<String, BithumbOrderbook>();

  public BithumbOrderbookAll(
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("payment_currency") String paymentCurrency) {
    this.timestamp = timestamp;
    this.paymentCurrency = paymentCurrency;
  }

  public Map<String, BithumbOrderbook> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, BithumbOrderbook value) {
    this.additionalProperties.put(name, value);
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getPaymentCurrency() {
    return paymentCurrency;
  }

  @Override
  public String toString() {
    return "BithumbOrderbookAll{"
        + "timestamp="
        + timestamp
        + ", paymentCurrency='"
        + paymentCurrency
        + '\''
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
