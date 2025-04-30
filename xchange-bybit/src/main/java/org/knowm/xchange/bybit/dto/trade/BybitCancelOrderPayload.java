package org.knowm.xchange.bybit.dto.trade;

import lombok.Getter;
import org.knowm.xchange.bybit.dto.BybitCategory;

@Getter
public class BybitCancelOrderPayload {

  private final BybitCategory category;
  private final String symbol;
  private final String orderId;
  private final String orderLinkId;
  private String orderFilter;

  public BybitCancelOrderPayload(
      BybitCategory category, String symbol, String orderId, String orderLinkId) {
    this.category = category;
    this.symbol = symbol;
    this.orderId = orderId;
    this.orderLinkId = orderLinkId;
  }
}
