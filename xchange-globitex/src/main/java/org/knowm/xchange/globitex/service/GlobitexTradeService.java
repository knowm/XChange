package org.knowm.xchange.globitex.service;

import java.io.IOException;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.globitex.GlobitexAdapters;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.service.trade.params.orders.*;

public class GlobitexTradeService extends GlobitexTradeServiceRaw implements TradeService {

  public GlobitexTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    return GlobitexAdapters.adaptToUserTrades(
        getGlobitexUserTrades((TradeHistoryParamsAll) params));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new TradeHistoryParamsAll();
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return GlobitexAdapters.adaptToOpenOrders(getGlobitexActiveOrders());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return GlobitexAdapters.adaptToOpenOrders(
        getGlobitexActiveOrders((OpenOrdersParamCurrencyPair) params));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return placeGlobitexMarketOrder(marketOrder).getObject().getClientOrderId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeGlobitexLimitOrder(limitOrder).getObject().getClientOrderId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String changeOrder(LimitOrder limitOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return (globitexCancelOrder(orderId).getObject().getOrderStatus().equals("CANCELLED"));
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
