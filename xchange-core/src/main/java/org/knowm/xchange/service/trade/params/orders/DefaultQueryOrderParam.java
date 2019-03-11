package org.knowm.xchange.service.trade.params.orders;

public class DefaultQueryOrderParam implements OrderQueryParams {

  private String orderId;

  public DefaultQueryOrderParam() {}

  public DefaultQueryOrderParam(String orderId) {

    this.orderId = orderId;
  }

  @Override
  public String getOrderId() {

    return orderId;
  }

  @Override
  public void setOrderId(String orderId) {

    this.orderId = orderId;
  }
}
