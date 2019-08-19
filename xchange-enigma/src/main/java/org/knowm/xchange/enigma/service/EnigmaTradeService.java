package org.knowm.xchange.enigma.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.enigma.model.EnigmaException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class EnigmaTradeService extends EnigmaTradeServiceRaw implements TradeService {

  public EnigmaTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, EnigmaException {
    return cancelOrder(Integer.parseInt(orderId)).isResult();
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return null;
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return null;
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return null;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return null;
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    return null;
  }

  @Override
  public String changeOrder(LimitOrder limitOrder) throws IOException {
    return null;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    return false;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    return null;
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return null;
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    return Collections.emptyList();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    return Collections.emptyList();
  }
}
