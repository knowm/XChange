package org.knowm.xchange.kucoin.dto.trading;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamCurrencyPair;

public class KucoinOrderQueryParams extends DefaultQueryOrderParamCurrencyPair {

  private Order.OrderType orderType;
  private Integer limit;
  private Integer page;

  public KucoinOrderQueryParams() {}

  public KucoinOrderQueryParams(
      CurrencyPair currencyPair, String orderOid, Order.OrderType orderType) {
    super(currencyPair, orderOid);
    this.orderType = orderType;
  }

  public KucoinOrderQueryParams(
      CurrencyPair currencyPair,
      String orderOid,
      Order.OrderType orderType,
      Integer limit,
      Integer page) {
    super(currencyPair, orderOid);
    this.orderType = orderType;
    this.limit = limit;
    this.page = page;
  }

  public Order.OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(Order.OrderType orderType) {
    this.orderType = orderType;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }
}
