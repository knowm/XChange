package org.knowm.xchange.cointrader.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cointrader.dto.CointraderBaseResponse;

public class CointraderSubmitOrderResponse extends CointraderBaseResponse<CointraderSubmitOrderResponse.OrderData> {

  public CointraderSubmitOrderResponse(@JsonProperty("success") Boolean success, @JsonProperty("message") String message,
      @JsonProperty("data") OrderData data) {
    super(success, message, data);
  }

  @Override
  public String toString() {
    return String.format("CointraderSubmitOrderResponse{orderId=%s}", getData().getId());
  }

  public static class OrderData {
    Long id;

    public Long getId() {
      return id;
    }

    @Override
    public String toString() {
      return String.valueOf(getId());
    }
  }
}
