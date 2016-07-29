package org.knowm.xchange.cointrader.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.knowm.xchange.cointrader.dto.CointraderRequest;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class CointraderCancelOrderRequest extends CointraderRequest {
  @JsonProperty
  @JsonSerialize(using = ToStringSerializer.class)
  private final Long orderId;

  public CointraderCancelOrderRequest(Long orderId) {
    this.orderId = orderId;
  }
}
