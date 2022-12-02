package org.knowm.xchange.coinbene.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbene.dto.CoinbeneAdapters;
import org.knowm.xchange.coinbene.dto.trading.CoinbeneLimitOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class CoinbeneTradeService extends CoinbeneTradeServiceRaw implements TradeService {
  public CoinbeneTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeCoinbeneLimitOrder(limitOrder).getOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelCoinbeneOrder(orderId).isOk();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    CoinbeneLimitOrder order = getCoinbeneOrder(orderQueryParams[0].getOrderId()).getOrder();
    return Collections.singletonList(CoinbeneAdapters.adaptLimitOrder(order));
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return getOpenOrders(((DefaultOpenOrdersParamCurrencyPair) params).getCurrencyPair());
  }

  public OpenOrders getOpenOrders(CurrencyPair pair) throws IOException {
    return CoinbeneAdapters.adaptOpenOrders(getCoinbeneOpenOrders(pair).getOrders());
  }
}
