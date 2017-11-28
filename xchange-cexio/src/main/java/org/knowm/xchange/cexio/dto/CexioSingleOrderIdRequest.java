package org.knowm.xchange.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CexioSingleOrderIdRequest extends CexIORequest {
  @JsonProperty("id")
  public final String orderId;

  public CexioSingleOrderIdRequest(String orderId) {
    this.orderId = orderId;
  }
}
