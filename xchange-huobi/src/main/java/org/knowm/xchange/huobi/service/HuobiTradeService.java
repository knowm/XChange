package org.knowm.xchange.huobi.service;

import java.io.IOException;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.huobi.HuobiAdapters;
import org.knowm.xchange.huobi.dto.trade.HuobiOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.CurrencyPairParam;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class HuobiTradeService extends HuobiTradeServiceRaw implements TradeService {
  public static IOrderFlags FOK = new IOrderFlags() {};
  public static IOrderFlags IOC = new IOrderFlags() {};

  public HuobiTradeService(Exchange exchange) {
    super(exchange);
  }

  /** Huobi trade history only goes back 48h - a new API was promised in 2019-Q1 */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams tradeHistoryParams) throws IOException {
    if (!(tradeHistoryParams instanceof CurrencyPairParam)) throw new IllegalArgumentException();

    HuobiOrder[] openOrders = getHuobiTradeHistory((CurrencyPairParam) tradeHistoryParams);
    return HuobiAdapters.adaptTradeHistory(openOrders);
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    return HuobiAdapters.adaptOrders(getHuobiOrder(orderIds));
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamCurrencyPair();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelHuobiOrder(orderId).length() > 0;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams cancelOrderParams) throws IOException {
    return cancelOrderParams instanceof CancelOrderByIdParams
        && cancelOrder(((CancelOrderByIdParams) cancelOrderParams).getOrderId());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return placeHuobiMarketOrder(marketOrder);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams openOrdersParams) throws IOException {
    if (!(openOrdersParams instanceof CurrencyPairParam)) throw new IllegalArgumentException();

    HuobiOrder[] openOrders = getHuobiOpenOrders((CurrencyPairParam) openOrdersParams);
    return HuobiAdapters.adaptOpenOrders(openOrders);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeHuobiLimitOrder(limitOrder);
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    return null;
  }
}
