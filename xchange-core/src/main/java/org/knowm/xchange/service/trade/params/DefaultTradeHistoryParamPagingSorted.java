package org.knowm.xchange.service.trade.params;

public class DefaultTradeHistoryParamPagingSorted extends DefaultTradeHistoryParamPaging
    implements TradeHistoryParamsSorted {

  private Order order = Order.asc;

  public DefaultTradeHistoryParamPagingSorted(Integer pageLength) {
    super(pageLength);
  }

  public DefaultTradeHistoryParamPagingSorted(Integer pageLength, Order order) {
    super(pageLength);
    this.order = order;
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
