package org.knowm.xchange.kucoin.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByOrderTypeParams;

public class KucoinCancelOrderParams
    implements CancelOrderByCurrencyPair, CancelOrderByIdParams, CancelOrderByOrderTypeParams {

  private CurrencyPair currencyPair;
  private String orderId;
  private OrderType orderType;

  public KucoinCancelOrderParams(CurrencyPair currencyPair, String orderId, OrderType orderType) {
    super();
    this.currencyPair = currencyPair;
    this.orderId = orderId;
    this.orderType = orderType;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }

  @Override
  public OrderType getOrderType() {
    return orderType;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }
}
