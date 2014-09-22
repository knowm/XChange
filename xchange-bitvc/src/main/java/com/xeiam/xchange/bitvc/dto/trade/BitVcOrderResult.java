package com.xeiam.xchange.bitvc.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcOrderResult {

  private final BitVcOrder[] orders;

  public BitVcOrderResult(@JsonProperty("orders") BitVcOrder[] orders) {

    this.orders = orders;
  }

  public BitVcOrder[] getOrders() {

    return orders;
  }
}
