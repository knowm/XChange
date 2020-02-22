package org.knowm.xchange.kucoin;

import static java.util.stream.Collectors.toCollection;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.kucoin.dto.response.*;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KucoinTradeService extends KucoinTradeServiceRaw implements TradeService {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  private static final int TRADE_HISTORIES_TO_FETCH = 500;
  private static final int ORDERS_TO_FETCH = 500;
  private static final long cutoffHistOrdersMillis =
      Date.from(java.time.LocalDate.of(2019, 2, 18).atStartOfDay(ZoneId.of("UTC+8")).toInstant())
          .getTime();
  private static final long oneWeekMillis = 7 * 24 * 60 * 60 * 1000;

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
      // TODO Currency pair is actually optional on KuCoin API, i.e. it should be possible to
      // proceed with genericParams = null
      Preconditions.checkArgument(
          genericParams instanceof TradeHistoryParamCurrencyPair,
          "Only currency pair parameters are currently supported.");
      TradeHistoryParamCurrencyPair params = (TradeHistoryParamCurrencyPair) genericParams;
      symbol = KucoinAdapters.adaptCurrencyPair(params.getCurrencyPair());
    }

    List<UserTrade> userTrades = new ArrayList<>();
    Long startTime = null;
    Long endTime = null;
    if (genericParams instanceof TradeHistoryParamsTimeSpan) {
      if (((TradeHistoryParamsTimeSpan) genericParams).getStartTime() != null) {
        startTime = ((TradeHistoryParamsTimeSpan) genericParams).getStartTime().getTime();
      }
      if (((TradeHistoryParamsTimeSpan) genericParams).getEndTime() != null) {
        endTime = ((TradeHistoryParamsTimeSpan) genericParams).getEndTime().getTime();
      }
      /*
        KuCoin restricts time spans to 7 days on new fills API but not hist-orders. I.e. you could request a whole year of
        trades before 2019-2-18 but only 7 days of trades after that date.
        It would be nice to check and enforce this rule here but that could end up being confusing for users.
      */
      if (startTime == null && endTime == null) {
        startTime = new Date().getTime() - oneWeekMillis;
        logger.warn(
            "No start or end time for trade history request specified, defaulting to last 7 days!");
      }
    }
    // TODO getKucoinFills(...).getItems will just get the current items and silently ignore any
    // other pages if present!
    Long startTimeSecs = startTime != null ? startTime / 1000 : null;
    Long endTimeSecs = endTime != null ? endTime / 1000 : null;

    if (startTime != null && startTime > cutoffHistOrdersMillis) {
      userTrades =
          getKucoinFills(symbol, 1, TRADE_HISTORIES_TO_FETCH, startTime, endTime).getItems()
              .stream()
              .map(KucoinAdapters::adaptUserTrade)
              .collect(Collectors.toList());
    } else if (endTime != null && endTime < cutoffHistOrdersMillis) {
      userTrades =
          getKucoinHistOrders(symbol, 1, TRADE_HISTORIES_TO_FETCH, startTimeSecs, endTimeSecs)
              .getItems().stream()
              .map(KucoinAdapters::adaptHistOrder)
              .collect(Collectors.toList());
    } else {
      userTrades =
          Stream.concat(
              getKucoinHistOrders(
                  symbol, 1, TRADE_HISTORIES_TO_FETCH, startTimeSecs, endTimeSecs)
                  .getItems().stream()
                  .map(KucoinAdapters::adaptHistOrder),
              getKucoinFills(symbol, 1, TRADE_HISTORIES_TO_FETCH, startTime, endTime).getItems()
                  .stream()
                  .map(KucoinAdapters::adaptUserTrade))
              .collect(toCollection(ArrayList::new));
    }

    return new UserTrades(userTrades, TradeSortType.SortByTimestamp);
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

  /**
   * TODO same as Binance. Should be merged into generic API
   */
  public interface KucoinOrderFlags extends IOrderFlags {
    String getClientId();
  }
}
