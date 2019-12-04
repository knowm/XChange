package org.knowm.xchange.kucoin;

import static java.util.stream.Collectors.toCollection;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.kucoin.dto.response.OrderCancelResponse;
import org.knowm.xchange.kucoin.dto.response.OrderResponse;
import org.knowm.xchange.kucoin.dto.response.TradeResponse;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class KucoinTradeService extends KucoinTradeServiceRaw implements TradeService {

  private static final int TRADE_HISTORIES_TO_FETCH = 500;
  private static final int ORDERS_TO_FETCH = 500;

  KucoinTradeService(KucoinExchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return convertOpenOrders(getKucoinOpenOrders(null, 1, ORDERS_TO_FETCH).getItems(), null);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    String symbol = null;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
      symbol = KucoinAdapters.adaptCurrencyPair(pairParams.getCurrencyPair());
    }
    return convertOpenOrders(
        getKucoinOpenOrders(symbol, 1, TRADE_HISTORIES_TO_FETCH).getItems(), params);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams genericParams) throws IOException {
    String symbol = null;
    if (genericParams != null) {
      Preconditions.checkArgument(
          genericParams instanceof TradeHistoryParamCurrencyPair,
          "Only currency pair parameters are currently supported.");
      TradeHistoryParamCurrencyPair params = (TradeHistoryParamCurrencyPair) genericParams;
      symbol = KucoinAdapters.adaptCurrencyPair(params.getCurrencyPair());
    }
    return convertUserTrades(getKucoinFills(symbol, 1, TRADE_HISTORIES_TO_FETCH).getItems());
  }

  @Override
  public OpenOrdersParamCurrencyPair createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamCurrencyPair();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    OrderCancelResponse response = kucoinCancelOrder(orderId);
    return response.getCancelledOrderIds().contains(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams genericParams) throws IOException {
    Preconditions.checkNotNull(genericParams, "No parameter supplied");
    Preconditions.checkArgument(
        genericParams instanceof CancelOrderByIdParams,
        "Only order id parameters are currently supported.");
    CancelOrderByIdParams params = (CancelOrderByIdParams) genericParams;
    return cancelOrder(params.getOrderId());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return kucoinCreateOrder(KucoinAdapters.adaptLimitOrder(limitOrder)).getOrderId();
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return kucoinCreateOrder(KucoinAdapters.adaptMarketOrder(marketOrder)).getOrderId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    return kucoinCreateOrder(KucoinAdapters.adaptStopOrder(stopOrder)).getOrderId();
  }

  private OpenOrders convertOpenOrders(Collection<OrderResponse> orders, OpenOrdersParams params) {
    Builder<LimitOrder> openOrders = ImmutableList.builder();
    Builder<Order> hiddenOrders = ImmutableList.builder();
    orders.stream()
        .map(KucoinAdapters::adaptOrder)
        .filter(o -> params == null ? true : params.accept(o))
        .forEach(
            o -> {
              if (o instanceof LimitOrder) {
                openOrders.add((LimitOrder) o);
              } else {
                hiddenOrders.add(o);
              }
            });
    return new OpenOrders(openOrders.build(), hiddenOrders.build());
  }

  private UserTrades convertUserTrades(List<TradeResponse> fills) {
    return new UserTrades(
        fills.stream().map(KucoinAdapters::adaptUserTrade).collect(toCollection(ArrayList::new)),
        TradeSortType.SortByTimestamp);
  }

  /** TODO same as Binance. Should be merged into generic API */
  public interface KucoinOrderFlags extends IOrderFlags {
    String getClientId();
  }
}
