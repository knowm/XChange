package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Value;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.trade.*;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.*;
import org.knowm.xchange.utils.Assert;

public class BinanceTradeService extends BinanceTradeServiceRaw implements TradeService {

  public BinanceTradeService(BinanceExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
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
      Instrument pair = null;
      if (params instanceof OpenOrdersParamInstrument) {
        pair = ((OpenOrdersParamInstrument) params).getInstrument();
      } else if (params instanceof OpenOrdersParamCurrencyPair) {
        pair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
      }

      return BinanceAdapters.adaptOpenOrders(
          openOrdersAllProducts(pair), pair instanceof FuturesContract);

    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder mo) throws IOException {
    return placeOrderAllProducts(OrderType.MARKET, mo, null, null, null, null, null, null);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    TimeInForce tif = getOrderFlag(limitOrder, TimeInForce.class).orElse(TimeInForce.GTC);
    OrderType type;
    if (limitOrder.hasFlag(org.knowm.xchange.binance.dto.trade.BinanceOrderFlags.LIMIT_MAKER)) {
      type = OrderType.LIMIT_MAKER;
      tif = null;
    } else {
      type = OrderType.LIMIT;
    }
    return placeOrderAllProducts(
        type, limitOrder, limitOrder.getLimitPrice(), null, null, null, null, tif);
  }

  @Override
  public String placeStopOrder(StopOrder order) throws IOException {
    // Time-in-force should not be provided for market orders but is required for
    // limit orders, order we only default it for limit orders. If the caller
    // specifies one for a market order, we don't remove it, since Binance might
    // allow
    // it at some point.
    TimeInForce tif =
        getOrderFlag(order, TimeInForce.class)
            .orElse(order.getLimitPrice() != null ? TimeInForce.GTC : null);
    Long trailingDelta =
        getOrderFlag(order, TrailingFlag.class).map(TrailingFlag::getTrailingBip).orElse(null);
    OrderType orderType = BinanceAdapters.adaptOrderType(order);

    return placeOrderAllProducts(
        orderType,
        order,
        order.getLimitPrice(),
        order.getStopPrice(),
        null,
        trailingDelta,
        order.getTrailValue(),
        tif);
  }

  private <T extends IOrderFlags> Optional<T> getOrderFlag(Order order, Class<T> clazz) {
    return (Optional<T>) order.getOrderFlags().stream()
        .filter(flag -> clazz.isAssignableFrom(flag.getClass()))
        .findFirst();
  }

  private String placeOrderAllProducts(
      OrderType type,
      Order order,
      BigDecimal limitPrice,
      BigDecimal stopPrice,
      BigDecimal quoteOrderQty,
      Long trailingDelta,
      BigDecimal callBackRate,
      TimeInForce tif)
      throws IOException {
    try {
      String orderId;

      if (order.getInstrument() instanceof FuturesContract) {
        orderId =
            newFutureOrder(
                    order.getInstrument(),
                    BinanceAdapters.convert(order.getType()),
                    type,
                    tif,
                    order.getOriginalAmount(),
                    order.hasFlag(
                        org.knowm.xchange.binance.dto.trade.BinanceOrderFlags.REDUCE_ONLY),
                    limitPrice,
                    order.getUserReference(),
                    stopPrice,
                    false,
                    null,
                    callBackRate,
                    null)
                .getOrderId();
      } else {
        orderId =
            Long.toString(
                newOrder(
                        order.getInstrument(),
                        BinanceAdapters.convert(order.getType()),
                        type,
                        tif,
                        order.getOriginalAmount(),
                        quoteOrderQty,
                        limitPrice,
                        order.getUserReference(),
                        stopPrice,
                        trailingDelta,
                        null,
                        null)
                    .orderId);
      }
      return orderId;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  public void placeTestOrder(
      OrderType type, Order order, BigDecimal limitPrice, BigDecimal stopPrice) throws IOException {
    placeTestOrder(type, order, limitPrice, stopPrice, null, null);
  }

  public void placeTestOrder(
      OrderType type,
      Order order,
      BigDecimal limitPrice,
      BigDecimal stopPrice,
      BigDecimal quoteOrderQty,
      Long trailingDelta)
      throws IOException {
    try {
      TimeInForce tif = getOrderFlag(order, TimeInForce.class).orElse(null);
      testNewOrder(
          order.getInstrument(),
          BinanceAdapters.convert(order.getType()),
          type,
          tif,
          order.getOriginalAmount(),
          quoteOrderQty,
          limitPrice,
          order.getUserReference(),
          stopPrice,
          trailingDelta,
          null);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {
    try {
      if (!(params instanceof CancelOrderByInstrument)
          && !(params instanceof CancelOrderByIdParams)) {
        throw new ExchangeException(
            "You need to provide the currency pair and the order id to cancel an order.");
      }
      assert params instanceof CancelOrderByInstrument;
      CancelOrderByInstrument paramInstrument = (CancelOrderByInstrument) params;
      CancelOrderByIdParams paramId = (CancelOrderByIdParams) params;
      cancelOrderAllProducts(
          paramInstrument.getInstrument(), BinanceAdapters.id(paramId.getOrderId()), null, null);

      return true;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(String orderId) {
    throw new ExchangeException("You need to provide the currency pair to cancel an order.");
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {CancelOrderByIdParams.class, CancelOrderByInstrument.class};
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    try {
      Assert.isTrue(
          params instanceof TradeHistoryParamInstrument,
          "You need to provide the instrument to get the user trades.");
      TradeHistoryParamInstrument pairParams = (TradeHistoryParamInstrument) params;
      Instrument pair = pairParams.getInstrument();
      if (pair == null) {
        throw new ExchangeException("You need to provide the instrument to get the user trades.");
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
          fromId = BinanceAdapters.id(idParams.getStartId());
        } catch (Throwable ignored) {
        }
      }
      if ((fromId != null) && (startTime != null || endTime != null)) {
        throw new ExchangeException(
            "You should either specify the id from which you get the user trades from or start and end times. If you specify both, Binance will only honour the fromId parameter.");
      }

      Integer limit = null;
      if (params instanceof TradeHistoryParamLimit) {
        TradeHistoryParamLimit limitParams = (TradeHistoryParamLimit) params;
        limit = limitParams.getLimit();
      }

      List<BinanceTrade> binanceTrades =
          myTradesAllProducts(pair, orderId, startTime, endTime, fromId, limit);

      return BinanceAdapters.adaptUserTrades(binanceTrades, pair instanceof FuturesContract);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... params) throws IOException {
    try {
      Collection<Order> orders = new ArrayList<>();
      for (OrderQueryParams param : params) {
        if (!(param instanceof OrderQueryParamInstrument)) {
          throw new ExchangeException(
              "Parameters must be an instance of OrderQueryParamInstrument");
        }
        OrderQueryParamInstrument orderQueryParamInstrument = (OrderQueryParamInstrument) param;
        if (orderQueryParamInstrument.getInstrument() == null
            || orderQueryParamInstrument.getOrderId() == null) {
          throw new ExchangeException(
              "You need to provide the currency pair and the order id to query an order.");
        }

        orders.add(
            BinanceAdapters.adaptOrder(
                orderStatusAllProducts(
                    orderQueryParamInstrument.getInstrument(),
                    BinanceAdapters.id(orderQueryParamInstrument.getOrderId()),
                    null),
                orderQueryParamInstrument.getInstrument() instanceof FuturesContract));
      }
      return orders;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public OpenPositions getOpenPositions() throws IOException {
    return new OpenPositions(BinanceAdapters.adaptOpenPositions(openPositions()));
  }

  @Override
  public Collection<String> cancelAllOrders(CancelAllOrders orderParams) throws IOException {

    if (!(orderParams instanceof CancelOrderByInstrument)) {
      throw new NotAvailableFromExchangeException(
          "Parameters must be an instance of " + CancelOrderByInstrument.class.getSimpleName());
    }

    Instrument instrument = ((CancelOrderByInstrument) orderParams).getInstrument();

    return cancelAllOpenOrdersAllProducts(instrument).stream()
        .map(binanceCancelledOrder -> Long.toString(binanceCancelledOrder.orderId))
        .collect(Collectors.toList());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new BinanceTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {

    return new DefaultOpenOrdersParamInstrument();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Class getRequiredOrderQueryParamClass() {
    return OrderQueryParamInstrument.class;
  }

  public interface BinanceOrderFlags extends IOrderFlags {

    static BinanceOrderFlags withClientId(String clientId) {
      return new ClientIdFlag(clientId);
    }

    /** Used in fields 'newClientOrderId' */
    String getClientId();
  }

  @Value
  static final class ClientIdFlag implements BinanceOrderFlags {

    private final String clientId;
  }
}
