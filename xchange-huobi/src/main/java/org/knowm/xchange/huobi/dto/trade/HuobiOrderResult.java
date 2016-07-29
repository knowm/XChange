package org.knowm.xchange.huobi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiOrderResult {

  private final HuobiOrder[] orders;

  public HuobiOrderResult(@JsonProperty("orders") HuobiOrder[] orders) {

    this.orders = orders;
  }

  public HuobiOrder[] getOrders() {

    return orders;
  }
}
