package org.knowm.xchange.gdax.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;
import org.knowm.xchange.gdax.dto.trade.GDAXIdResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXPlaceLimitOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXPlaceMarketOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXPlaceOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXTradeHistoryParams;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

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
    GDAXPlaceMarketOrder gdaxMarketOrder = GDAXAdapters.adaptGDAXPlaceMarketOrder(marketOrder);
    GDAXIdResponse response = placeGDAXOrder(gdaxMarketOrder);
    return response.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, FundsExceededException {
    GDAXPlaceLimitOrder gdaxLimitOrder = GDAXAdapters.adaptGDAXPlaceLimitOrder(limitOrder);
    GDAXIdResponse response = placeGDAXOrder(gdaxLimitOrder);
    return response.getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException, FundsExceededException {
    GDAXPlaceOrder gdaxStopOrder = GDAXAdapters.adaptGDAXStopOrder(stopOrder);
    GDAXIdResponse response = placeGDAXOrder(gdaxStopOrder);
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
      orders.add(GDAXAdapters.adaptOrder(super.getOrder(orderId)));
    }

    return orders;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    return getOrder(
        Arrays.stream(orderQueryParams).map(OrderQueryParams::getOrderId).toArray(String[]::new));
  }
}
