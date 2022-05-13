package org.knowm.xchange.bitrue.service;

import lombok.Value;
import org.knowm.xchange.bitrue.BitrueAdapters;
import org.knowm.xchange.bitrue.BitrueAuthenticated;
import org.knowm.xchange.bitrue.BitrueErrorAdapter;
import org.knowm.xchange.bitrue.BitrueExchange;
import org.knowm.xchange.bitrue.dto.BitrueException;
import org.knowm.xchange.bitrue.dto.trade.*;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.*;
import org.knowm.xchange.utils.Assert;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BitrueTradeService extends BitrueTradeServiceRaw implements TradeService {

  public BitrueTradeService(
      BitrueExchange exchange,
      BitrueAuthenticated bitrue,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, bitrue, resilienceRegistries);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(new DefaultOpenOrdersParam());
  }

  public OpenOrders getOpenOrders(CurrencyPair pair) throws IOException {
    return getOpenOrders(new DefaultOpenOrdersParamCurrencyPair(pair));
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    try {
      List<BitrueOrder> binanceOpenOrders;
      if (params instanceof OpenOrdersParamCurrencyPair) {
        OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
        CurrencyPair pair = pairParams.getCurrencyPair();
        binanceOpenOrders = super.openOrders(pair);
      } else {
        binanceOpenOrders = super.openOrders();
      }

      List<LimitOrder> limitOrders = new ArrayList<>();
      List<Order> otherOrders = new ArrayList<>();
      binanceOpenOrders.forEach(
          binanceOrder -> {
            Order order = BitrueAdapters.adaptOrder(binanceOrder);
            if (order instanceof LimitOrder) {
              limitOrders.add((LimitOrder) order);
            } else {
              otherOrders.add(order);
            }
          });
      return new OpenOrders(limitOrders, otherOrders);
    } catch (BitrueException e) {
      throw BitrueErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder mo) throws IOException {
    return placeOrder(OrderType.MARKET, mo, null, null, null);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    TimeInForce tif = timeInForceFromOrder(limitOrder).orElse(TimeInForce.GTC);
    OrderType type;
    if (limitOrder.hasFlag(org.knowm.xchange.bitrue.dto.trade.BitrueOrderFlags.LIMIT_MAKER)) {
      type = OrderType.LIMIT_MAKER;
      tif = null;
    } else {
      type = OrderType.LIMIT;
    }
    return placeOrder(type, limitOrder, limitOrder.getLimitPrice(), null, tif);
  }

  @Override
  public String placeStopOrder(StopOrder order) throws IOException {
    // Time-in-force should not be provided for market orders but is required for
    // limit orders, order we only default it for limit orders. If the caller
    // specifies one for a market order, we don't remove it, since Bitrue might
    // allow
    // it at some point.
    TimeInForce tif =
        timeInForceFromOrder(order).orElse(order.getLimitPrice() != null ? TimeInForce.GTC : null);

    OrderType orderType = BitrueAdapters.adaptOrderType(order);

    return placeOrder(orderType, order, order.getLimitPrice(), order.getStopPrice(), tif);
  }

  private Optional<TimeInForce> timeInForceFromOrder(Order order) {
    return order.getOrderFlags().stream()
        .filter(flag -> flag instanceof TimeInForce)
        .map(flag -> (TimeInForce) flag)
        .findFirst();
  }

  private String placeOrder(
      OrderType type, Order order, BigDecimal limitPrice, BigDecimal stopPrice, TimeInForce tif)
      throws IOException {
    try {
      Long recvWindow =
          (Long)
              exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
      BitrueNewOrder newOrder =
          newOrder(
              order.getCurrencyPair(),
              BitrueAdapters.convert(order.getType()),
              type,
              tif,
              order.getOriginalAmount(),
              limitPrice,
              getClientOrderId(order),
              stopPrice,
              null,
              null);
      return Long.toString(newOrder.orderId);
    } catch (BitrueException e) {
      throw BitrueErrorAdapter.adapt(e);
    }
  }

  private String getClientOrderId(Order order) {

    String clientOrderId = null;
    for (IOrderFlags flags : order.getOrderFlags()) {
      if (flags instanceof BitrueOrderFlags) {
        BitrueOrderFlags bof = (BitrueOrderFlags) flags;
        if (clientOrderId == null) {
          clientOrderId = bof.getClientId();
        }
      }
    }
    return clientOrderId;
  }

  @Override
  public boolean cancelOrder(String orderId) {
    throw new ExchangeException("You need to provide the currency pair to cancel an order.");
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {
    try {
      if (!(params instanceof CancelOrderByCurrencyPair)
          && !(params instanceof CancelOrderByIdParams)) {
        throw new ExchangeException(
            "You need to provide the currency pair and the order id to cancel an order.");
      }
      CancelOrderByCurrencyPair paramCurrencyPair = (CancelOrderByCurrencyPair) params;
      CancelOrderByIdParams paramId = (CancelOrderByIdParams) params;
      super.cancelOrder(
          paramCurrencyPair.getCurrencyPair(),
          BitrueAdapters.id(paramId.getOrderId()),
          null,
          null);
      return true;
    } catch (BitrueException e) {
      throw BitrueErrorAdapter.adapt(e);
    }
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {CancelOrderByIdParams.class, CancelOrderByCurrencyPair.class};
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    try {
      Assert.isTrue(
          params instanceof TradeHistoryParamCurrencyPair,
          "You need to provide the currency pair to get the user trades.");
      TradeHistoryParamCurrencyPair pairParams = (TradeHistoryParamCurrencyPair) params;
      CurrencyPair pair = pairParams.getCurrencyPair();
      if (pair == null) {
        throw new ExchangeException(
            "You need to provide the currency pair to get the user trades.");
      }
      Long orderId = null;
      Long startTime = null;
      Long endTime = null;
      if (params instanceof TradeHistoryParamsTimeSpan) {
        if (((TradeHistoryParamsTimeSpan) params).getStartTime() != null) {
          startTime = ((TradeHistoryParamsTimeSpan) params).getStartTime().getTime();
        }
        if (((TradeHistoryParamsTimeSpan) params).getEndTime() != null) {
          endTime = ((TradeHistoryParamsTimeSpan) params).getEndTime().getTime();
        }
      }
      Long fromId = null;
      if (params instanceof TradeHistoryParamsIdSpan) {
        TradeHistoryParamsIdSpan idParams = (TradeHistoryParamsIdSpan) params;
        try {
          fromId = BitrueAdapters.id(idParams.getStartId());
        } catch (Throwable ignored) {
        }
      }
      if ((fromId != null) && (startTime != null || endTime != null)) {
        throw new ExchangeException(
            "You should either specify the id from which you get the user trades from or start and end times. If you specify both, Bitrue will only honour the fromId parameter.");
      }

      Integer limit = null;
      if (params instanceof TradeHistoryParamLimit) {
        TradeHistoryParamLimit limitParams = (TradeHistoryParamLimit) params;
        limit = limitParams.getLimit();
      }

      List<BitrueTrade> binanceTrades =
          super.myTrades(pair, orderId, startTime, endTime, fromId, limit);
      List<UserTrade> trades =
          binanceTrades.stream()
              .map(
                  t ->
                      new UserTrade.Builder()
                          .type(BitrueAdapters.convertType(t.isBuyer))
                          .originalAmount(t.qty)
                          .currencyPair(pair)
                          .price(t.price)
                          .timestamp(t.getTime())
                          .id(Long.toString(t.id))
                          .orderId(Long.toString(t.orderId))
                          .feeAmount(t.commission)
                          .feeCurrency(Currency.getInstance(t.commissionAsset))
                          .build())
              .collect(Collectors.toList());
      long lastId = binanceTrades.stream().map(t -> t.id).max(Long::compareTo).orElse(0L);
      return new UserTrades(trades, lastId, Trades.TradeSortType.SortByTimestamp);
    } catch (BitrueException e) {
      throw BitrueErrorAdapter.adapt(e);
    }
  }


  @Override
  public OpenOrdersParams createOpenOrdersParams() {

    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... params) throws IOException {
    try {
      Collection<Order> orders = new ArrayList<>();
      for (OrderQueryParams param : params) {
        if (!(param instanceof OrderQueryParamCurrencyPair)) {
          throw new ExchangeException(
              "Parameters must be an instance of OrderQueryParamCurrencyPair");
        }
        OrderQueryParamCurrencyPair orderQueryParamCurrencyPair =
            (OrderQueryParamCurrencyPair) param;
        if (orderQueryParamCurrencyPair.getCurrencyPair() == null
            || orderQueryParamCurrencyPair.getOrderId() == null) {
          throw new ExchangeException(
              "You need to provide the currency pair and the order id to query an order.");
        }

        orders.add(
            BitrueAdapters.adaptOrder(
                super.orderStatus(
                    orderQueryParamCurrencyPair.getCurrencyPair(),
                    BitrueAdapters.id(orderQueryParamCurrencyPair.getOrderId()),
                    null)));
      }
      return orders;
    } catch (BitrueException e) {
      throw BitrueErrorAdapter.adapt(e);
    }
  }

  public interface BitrueOrderFlags extends IOrderFlags {

    static BitrueOrderFlags withClientId(String clientId) {
      return new ClientIdFlag(clientId);
    }

    /** Used in fields 'newClientOrderId' */
    String getClientId();
  }

  @Value
  static final class ClientIdFlag implements BitrueOrderFlags {
    private final String clientId;
  }
}
