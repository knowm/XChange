package org.knowm.xchange.cointrader.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cointrader.dto.CointraderBaseResponse;

public class CointraderOpenOrdersResponse extends CointraderBaseResponse<CointraderOrder[]> {
  protected CointraderOpenOrdersResponse(@JsonProperty("success") Boolean success, @JsonProperty("message") String message,
      @JsonProperty("data") CointraderOrder[] data) {
    super(success, message, data);
  }
}
