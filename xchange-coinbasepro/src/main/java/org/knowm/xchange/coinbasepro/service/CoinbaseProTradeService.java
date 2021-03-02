package org.knowm.xchange.coinbasepro.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProFill;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProIdResponse;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProOrder;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProPlaceLimitOrder;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProPlaceMarketOrder;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProPlaceOrder;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProTradeHistoryParams;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class CoinbaseProTradeService extends CoinbaseProTradeServiceRaw implements TradeService {

  public CoinbaseProTradeService(Exchange exchange) {
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

    CoinbaseProOrder[] coinbaseExOpenOrders = getCoinbaseProOpenOrders();
    return CoinbaseProAdapters.adaptOpenOrders(coinbaseExOpenOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    CoinbaseProPlaceMarketOrder coinbaseProMarketOrder =
        CoinbaseProAdapters.adaptCoinbaseProPlaceMarketOrder(marketOrder);
    CoinbaseProIdResponse response = placeCoinbaseProOrder(coinbaseProMarketOrder);
    return response.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, FundsExceededException {
    CoinbaseProPlaceLimitOrder coinbaseProLimitOrder =
        CoinbaseProAdapters.adaptCoinbaseProPlaceLimitOrder(limitOrder);
    CoinbaseProIdResponse response = placeCoinbaseProOrder(coinbaseProLimitOrder);
    return response.getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException, FundsExceededException {
    CoinbaseProPlaceOrder coinbaseProStopOrder =
        CoinbaseProAdapters.adaptCoinbaseProStopOrder(stopOrder);
    CoinbaseProIdResponse response = placeCoinbaseProOrder(coinbaseProStopOrder);
    return response.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return cancelCoinbaseProOrder(orderId);
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

    CoinbaseProFill[] coinbaseExFills = getCoinbaseProFills(params);
    return CoinbaseProAdapters.adaptTradeHistory(coinbaseExFills);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new CoinbaseProTradeHistoryParams();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    Collection<Order> orders = new ArrayList<>(orderIds.length);

    for (String orderId : orderIds) {
      orders.add(CoinbaseProAdapters.adaptOrder(super.getOrder(orderId)));
    }

    return orders;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    return getOrder(
        Arrays.stream(orderQueryParams).map(OrderQueryParams::getOrderId).toArray(String[]::new));
  }
}
