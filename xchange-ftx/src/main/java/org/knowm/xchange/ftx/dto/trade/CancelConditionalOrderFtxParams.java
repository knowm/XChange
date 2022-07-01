package org.knowm.xchange.ftx.dto.trade;

import org.knowm.xchange.service.trade.params.CancelOrderParams;

public class CancelConditionalOrderFtxParams implements CancelOrderParams {

  private final String orderId;

  public CancelConditionalOrderFtxParams(String orderId) {
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}
