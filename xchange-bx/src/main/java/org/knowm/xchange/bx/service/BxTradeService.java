package org.knowm.xchange.bx.service;

import java.io.IOException;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bx.BxAdapters;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BxTradeService extends BxTradeServiceRaw implements TradeService {

  public BxTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams openOrdersParams) throws IOException {
    return BxAdapters.adaptOpenOrders(getBxOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeBxLimitOrder(limitOrder);
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelBxOrder(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams cancelOrderParams) throws IOException {
    return cancelOrderParams instanceof CancelOrderByIdParams
        && cancelBxOrder(((CancelOrderByIdParams) cancelOrderParams).getOrderId());
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams tradeHistoryParams) throws IOException {
    return BxAdapters.adaptUserTrades(getBxTradeHistory(tradeHistoryParams));
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
    return getBxOrder(orderIds);
  }
}
