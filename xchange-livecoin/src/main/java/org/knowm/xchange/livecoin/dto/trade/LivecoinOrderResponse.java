package org.knowm.xchange.livecoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.livecoin.dto.LivecoinBaseResponse;

/** @author walec51 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LivecoinOrderResponse extends LivecoinBaseResponse {

  private final String orderId;

  public LivecoinOrderResponse(
      @JsonProperty("success") Boolean success, @JsonProperty("orderId") String orderId) {
    super(success);
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}
