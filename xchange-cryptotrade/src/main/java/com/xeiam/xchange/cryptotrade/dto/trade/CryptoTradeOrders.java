package com.xeiam.xchange.cryptotrade.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;

public class CryptoTradeOrders extends CryptoTradeBaseResponse {

  private final List<CryptoTradeOrder> orders;

  private CryptoTradeOrders(@JsonProperty("data") List<CryptoTradeOrder> orders, @JsonProperty("status") String status,
      @JsonProperty("error") String error) {

    super(status, error);
    this.orders = orders;
  }

  public List<CryptoTradeOrder> getOrders() {

    return orders;
  }

  @Override
  public String toString() {

    return "CryptoTradeOrders [orders=" + orders + "]";
  }
}
