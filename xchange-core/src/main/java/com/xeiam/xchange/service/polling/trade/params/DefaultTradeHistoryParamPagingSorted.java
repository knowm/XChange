package com.xeiam.xchange.service.polling.trade.params;

public class DefaultTradeHistoryParamPagingSorted extends DefaultTradeHistoryParamPaging implements TradeHistoryParamsSorted {

  private Order order = Order.asc;

  public DefaultTradeHistoryParamPagingSorted(Integer pageLength) {
    super(pageLength);
  }

  @Override
  public Order getOrder() {
    return order;
  }

  @Override
  public void setOrder(Order order) {
    this.order = order;
  }
}
