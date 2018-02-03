package org.knowm.xchange.gdax.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;
import org.knowm.xchange.gdax.dto.trade.GDAXIdResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXTradeHistoryParams;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import si.mazi.rescu.HttpStatusExceptionSupport;

public class GDAXTradeService extends GDAXTradeServiceRaw implements TradeService {

  public GDAXTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {

    GDAXOrder[] coinbaseExOpenOrders = getGDAXOpenOrders();
    return GDAXAdapters.adaptOpenOrders(coinbaseExOpenOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    GDAXIdResponse response = placeGDAXMarketOrder(marketOrder);
    return response.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, FundsExceededException {

    GDAXIdResponse response = placeGDAXLimitOrder(limitOrder);
    return response.getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException, FundsExceededException {

    GDAXIdResponse response = placeGDAXStopOrder(stopOrder);
    return response.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return cancelGDAXOrder(orderId);
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

    GDAXFill[] coinbaseExFills = getGDAXFills(params);
    return GDAXAdapters.adaptTradeHistory(coinbaseExFills);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new GDAXTradeHistoryParams();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    Collection<Order> orders = new ArrayList<>(orderIds.length);

    for (String orderId : orderIds) {
      try {
        final GDAXOrder order = super.getOrder(orderId);
        orders.add(GDAXAdapters.adaptOrder(order));
      } catch (final ExchangeException e) {
        // We get 404 for cancelled orders. This is a bit of a pain....
        if ( e.getCause() instanceof HttpStatusExceptionSupport && ((HttpStatusExceptionSupport)e.getCause()).getHttpStatusCode() == 404) {
          // We can't actually tell, if we get a 404, whether it was a market or limit order, or
          // whether it was a bid or an ask and so on. This makes things very tricky.  Let's
          // put a take in the ground and say you get get neither, and lots of nulls. At least
          // the caller gets a consistent behaviour they can code to.
          orders.add(new Order(null, null, null, orderId, null, null, null, Order.OrderStatus.CANCELED) { });
        } else {
          throw e;
        }
      }
    }

    return orders;
  }
}
