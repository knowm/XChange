package com.xeiam.xchange.cryptotrade.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;

public class CryptoTradeOrderInfoReturn extends CryptoTradeBaseResponse {

  private final List<CryptoTradeOrder> orders;

  private CryptoTradeOrderInfoReturn(@JsonProperty("data") List<CryptoTradeOrder> orders, @JsonProperty("status") String status,
      @JsonProperty("error") String error) {

    super(status, error);
    /*
     * XXX: CryptoTrade 'orderinfo' API accepts only a single orderId argument, but it returns an array.
     */
    this.orders = orders;
  }

  public CryptoTradeOrder getOrder() {

    return orders.get(0);
  }

  @Override
  public String toString() {

    return "CryptoTradeOrderInfoReturn [order=" + orders + "]";
  }

}
