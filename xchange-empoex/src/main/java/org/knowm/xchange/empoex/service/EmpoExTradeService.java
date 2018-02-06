package org.knowm.xchange.empoex.service;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.empoex.EmpoExAdapters;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class EmpoExTradeService extends EmpoExTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public EmpoExTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws IOException {
    return EmpoExAdapters.adaptOpenOrders(super.getEmpoExOpenOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    if (limitOrder.getType() == OrderType.BID) {
      return super.buy(limitOrder);
    } else {
      return super.sell(limitOrder);
    }
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return super.cancel(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
