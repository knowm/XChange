package org.knowm.xchange.bl3p.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bl3p.dto.Bl3pResult;

public class Bl3pNewOrder extends Bl3pResult<Bl3pNewOrder.Bl3pMarketOrderData> {

  public static class Bl3pMarketOrderData {
    @JsonProperty("order_id")
    private int orderId;

    public int getOrderId() {
      return this.orderId;
    }
  }
}
