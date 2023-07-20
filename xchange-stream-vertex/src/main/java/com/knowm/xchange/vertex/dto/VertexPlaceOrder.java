package com.knowm.xchange.vertex.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VertexPlaceOrder {
  private final long product_id;
  private final VertexOrder order;
  private final String signature;
  private final Boolean spot_leverage;

  public VertexPlaceOrder(long productId, VertexOrder order, String signature, Boolean spotLeverage) {
    product_id = productId;
    this.order = order;
    this.signature = signature;
    spot_leverage = spotLeverage;
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public Boolean getSpot_leverage() {
    return spot_leverage;
  }
}
