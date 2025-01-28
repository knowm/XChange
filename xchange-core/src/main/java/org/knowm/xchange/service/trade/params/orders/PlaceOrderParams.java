package org.knowm.xchange.service.trade.params.orders;

public interface PlaceOrderParams {
  <T> T getOrderParam(String param, Class<T> type);
}
