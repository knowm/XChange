package org.knowm.xchange.service.trade.params;

/**
 * {@link TradeHistoryParams} with order id as param
 */
public interface TradeHistoryParamOrderId extends TradeHistoryParams {

  String getOrderId();

  void setOrderId(String orderId);

}
