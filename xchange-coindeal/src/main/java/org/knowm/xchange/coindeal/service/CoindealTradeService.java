package org.knowm.xchange.coindeal.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindeal.CoindealAdapters;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

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
    if (!(params instanceof TradeHistoryParamInstrument)) {
      throw new IOException(
          "TradeHistoryParams must implement TradeHistoryParam class with currencyPair support!");
    }
    Integer limit = 100;
    if (params instanceof TradeHistoryParamLimit
        && ((TradeHistoryParamLimit) params).getLimit() != null) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }
    return CoindealAdapters.adaptToUserTrades(
        getCoindealTradeHistory(((TradeHistoryParamInstrument) params).getCurrencyPair(), limit));
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (params instanceof OpenOrdersParamInstrument) {
      return CoindealAdapters.adaptToOpenOrders(
          getCoindealActiveOrders(((OpenOrdersParamInstrument) params).getCurrencyPair()));
    } else {
      throw new ExchangeException("Currency pair required!");
    }
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    return Collections.singletonList(
        CoindealAdapters.adaptOrder(getCoindealOrderById(orderIds[0])));
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamInstrument();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new TradeHistoryParamsAll();
  }
}
