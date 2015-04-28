package com.xeiam.xchange.cointrader.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xeiam.xchange.cointrader.dto.CointraderRequest;

public class CointraderCancelOrderRequest extends CointraderRequest {
  @JsonProperty
  @JsonSerialize(using = ToStringSerializer.class)
  private final Long id;

  public CointraderCancelOrderRequest(Long id) {
    this.id = id;
  }
}
