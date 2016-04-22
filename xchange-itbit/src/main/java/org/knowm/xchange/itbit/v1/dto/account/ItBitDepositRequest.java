package org.knowm.xchange.itbit.v1.dto.account;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItBitDepositRequest {

  private final String currency;
  private final Map<String, String> metadata;

  public ItBitDepositRequest(String currency, Map<String, String> metadata) {

    this.currency = currency;
    this.metadata = metadata;
  }

  @JsonProperty("currency")
  public String getCurrency() {

    return currency;
  }

  @JsonProperty("metadata")
  public Map<String, String> getMetadata() {

    return metadata;
  }
}
