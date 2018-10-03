package org.knowm.xchange.btcmarkets.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsBaseResponse;

public class BTCMarketsPlaceOrderResponse extends BTCMarketsBaseResponse {

  private final String clientRequestId;
  private final Long id;

  public BTCMarketsPlaceOrderResponse(
      @JsonProperty("success") Boolean success,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("errorCode") Integer errorCode,
      @JsonProperty("clientRequestId") String clientRequestId,
      @JsonProperty("id") Long id) {
    super(success, errorMessage, errorCode);
    this.clientRequestId = clientRequestId;
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getClientRequestId() {
    return clientRequestId;
  }
}
