package org.knowm.xchange.coindeal.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindeal.CoindealAdapters;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public final class CoindealTradeService extends CoindealTradeServiceRaw implements TradeService {

  public CoindealTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeOrder(limitOrder).getClientOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    cancelCoindealOrderById(orderId);
    return true;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    if (!(params instanceof TradeHistoryParamCurrencyPair)) {
      throw new IOException(
          "TradeHistoryParams must implement TradeHistoryParam class with currencyPair support!");
    }
    Integer limit = 100;
    if (params instanceof TradeHistoryParamLimit
        && ((TradeHistoryParamLimit) params).getLimit() != null) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }
    return CoindealAdapters.adaptToUserTrades(
        getCoindealTradeHistory(((TradeHistoryParamCurrencyPair) params).getCurrencyPair(), limit));
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (params instanceof OpenOrdersParamCurrencyPair) {
      return CoindealAdapters.adaptToOpenOrders(
          getCoindealActiveOrders(((OpenOrdersParamCurrencyPair) params).getCurrencyPair()));
    } else {
      throw new ExchangeException("Currency pair required!");
    }
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    return Collections.singletonList(
        CoindealAdapters.adaptOrder(getCoindealOrderById(orderQueryParams[0].getOrderId())));
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new TradeHistoryParamsAll();
  }
}
