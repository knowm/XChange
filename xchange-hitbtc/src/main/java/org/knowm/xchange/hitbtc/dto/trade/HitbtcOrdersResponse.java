package org.knowm.xchange.hitbtc.dto.trade;

import java.util.Arrays;

import org.knowm.xchange.hitbtc.dto.HitbtcBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcOrdersResponse extends HitbtcBaseResponse {

  HitbtcOrder[] orders;

  public HitbtcOrdersResponse(@JsonProperty("orders") HitbtcOrder[] orders) {

    super();
    this.orders = orders;
  }

  public HitbtcOrder[] getOrders() {

    return orders;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcOrdersResponse [orders=");
    builder.append(Arrays.toString(orders));
    builder.append("]");
    return builder.toString();
  }

}
