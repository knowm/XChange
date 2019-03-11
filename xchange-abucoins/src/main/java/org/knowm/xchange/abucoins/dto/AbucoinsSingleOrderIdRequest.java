package org.knowm.xchange.abucoins.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbucoinsSingleOrderIdRequest extends AbucoinsRequest {
  @JsonProperty("id")
  public final String orderId;

  public AbucoinsSingleOrderIdRequest(String orderId) {
    this.orderId = orderId;
  }
}
