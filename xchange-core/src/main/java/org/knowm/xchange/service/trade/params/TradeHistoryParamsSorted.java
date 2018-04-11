package org.knowm.xchange.service.trade.params;

public interface TradeHistoryParamsSorted extends TradeHistoryParams {

  Order getOrder();

  void setOrder(Order order);

  enum Order {
    asc,
    desc
  }
}
