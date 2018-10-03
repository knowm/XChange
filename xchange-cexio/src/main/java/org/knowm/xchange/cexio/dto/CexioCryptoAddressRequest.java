package org.knowm.xchange.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CexioCryptoAddressRequest extends CexIORequest {
  @JsonProperty("currency")
  public final String currency;

  public CexioCryptoAddressRequest(String currency) {
    this.currency = currency;
  }
}
