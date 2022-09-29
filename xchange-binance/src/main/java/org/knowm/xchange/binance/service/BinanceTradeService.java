package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Value;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.trade.*;
import org.knowm.xchange.binance.dto.trade.margin.BinanceNewMarginOrder;
import org.knowm.xchange.binance.dto.trade.margin.MarginAccountType;
import org.knowm.xchange.binance.dto.trade.margin.MarginSideEffectType;
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

public class BinanceTradeService extends BinanceTradeServiceRaw implements TradeService {

  private final boolean includeMarginAccountTypeInOrderId;

  public BinanceTradeService(
      BinanceExchange exchange,
      BinanceAuthenticated binance,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, binance, resilienceRegistries);

    this.includeMarginAccountTypeInOrderId = Boolean.TRUE.equals(
            exchange.getExchangeSpecification().getExchangeSpecificParametersItem(BinanceExchange.SPECIFIC_PARAM_INCLUDE_MARGIN_ACCOUNT_TYPE_IN_ORDER_ID));
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
      List<BinanceOrder> binanceOpenOrders;
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
            Order order = BinanceAdapters.adaptOrder(binanceOrder, null);
            if (order instanceof LimitOrder) {
              limitOrders.add((LimitOrder) order);
            } else {
              otherOrders.add(order);
            }
          });
      return new OpenOrders(limitOrders, otherOrders);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder mo) throws IOException {
    return placeOrder(OrderType.MARKET, mo, null, null, null, null, null, mo.getOrderFlags());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    TimeInForce tif = timeInForceFromOrder(limitOrder).orElse(TimeInForce.GTC);
    OrderType type;
    if (limitOrder.hasFlag(org.knowm.xchange.binance.dto.trade.BinanceOrderFlags.LIMIT_MAKER)) {
      type = OrderType.LIMIT_MAKER;
      tif = null;
    } else {
      type = OrderType.LIMIT;
    }
    return placeOrder(type, limitOrder, limitOrder.getLimitPrice(), null, null, null, tif, limitOrder.getOrderFlags());
  }

  @Override
  public String placeStopOrder(StopOrder order) throws IOException {
    // Time-in-force should not be provided for market orders but is required for
    // limit orders, order we only default it for limit orders. If the caller
    // specifies one for a market order, we don't remove it, since Binance might
    // allow
    // it at some point.
    TimeInForce tif =
        timeInForceFromOrder(order).orElse(order.getLimitPrice() != null ? TimeInForce.GTC : null);

    OrderType orderType = BinanceAdapters.adaptOrderType(order);

    return placeOrder(
        orderType, order, order.getLimitPrice(), order.getStopPrice(), null, null, tif, order.getOrderFlags());
  }

  private Optional<TimeInForce> timeInForceFromOrder(Order order) {
    return order.getOrderFlags().stream()
        .filter(flag -> flag instanceof TimeInForce)
        .map(flag -> (TimeInForce) flag)
        .findFirst();
  }

  private String placeOrder(
      OrderType type,
      Order order,
      BigDecimal limitPrice,
      BigDecimal stopPrice,
      BigDecimal quoteOrderQty,
      Long trailingDelta,
      TimeInForce tif,
      Set<IOrderFlags> orderFlags)
      throws IOException {
    try {
      Long recvWindow =
          (Long)
              exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");

      MarginAccountType marginAccountType = IOrderFlags.getOrderFlagOfType(orderFlags, MarginAccountType.class);

      if (marginAccountType == null) {
        BinanceNewOrder newOrder =
                newOrder(
                        order.getCurrencyPair(),
                        BinanceAdapters.convert(order.getType()),
                        type,
                        tif,
                        order.getOriginalAmount(),
                        quoteOrderQty, // TODO (BigDecimal)order.getExtraValue("quoteOrderQty")
                        limitPrice,
                        getClientOrderId(order),
                        stopPrice,
                        trailingDelta, // TODO (Long)order.getExtraValue("trailingDelta")
                        null,
                        null);
        return BinanceAdapters.placedOrderId(newOrder.orderId, null);
      } else {
        MarginSideEffectType marginSideEffect = IOrderFlags.getOrderFlagOfType(orderFlags, MarginSideEffectType.class);

        BinanceNewMarginOrder newOrder =
                newMarginOrder(
                        order.getCurrencyPair(),
                        BinanceAdapters.convert(order.getType()),
                        marginAccountType,
                        type,
                        tif,
                        order.getOriginalAmount(),
                        limitPrice,
                        getClientOrderId(order),
                        stopPrice,
                        null,
                        null,
                        marginSideEffect);

        return BinanceAdapters.placedOrderId(newOrder.orderId, includeMarginAccountTypeInOrderId ? marginAccountType : null);
      }
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
      TimeInForce tif = timeInForceFromOrder(order).orElse(null);
      Long recvWindow =
          (Long)
              exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
      testNewOrder(
          order.getCurrencyPair(),
          BinanceAdapters.convert(order.getType()),
          type,
          tif,
          order.getOriginalAmount(),
          quoteOrderQty,
          limitPrice,
          getClientOrderId(order),
          stopPrice,
          trailingDelta,
          null);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  private String getClientOrderId(Order order) {
    BinanceOrderFlags bof = IOrderFlags.getOrderFlagOfType(order.getOrderFlags(), BinanceOrderFlags.class);
    return bof != null ? bof.getClientId() : null;
  }

  @Override
  public boolean cancelOrder(String orderId) {
    throw new ExchangeException("You need to provide the currency pair to cancel an order.");
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {
    try {
      if (!(params instanceof CancelOrderByCurrencyPair)
          || !(params instanceof CancelOrderByIdParams)) {
        throw new ExchangeException(
            "You need to provide the currency pair and the order id to cancel an order.");
      }
      CancelOrderByCurrencyPair paramCurrencyPair = (CancelOrderByCurrencyPair) params;
      CancelOrderByIdParams paramId = (CancelOrderByIdParams) params;
      String orderId = paramId.getOrderId();
      MarginAccountType marginAccountType =
              params instanceof BinanceCancelOrderParams ? ((BinanceCancelOrderParams) params).getMarginAccountType()
                      : BinanceAdapters.getMarginAccountTypeFromOrderId(orderId);
      if (marginAccountType == null) {
        super.cancelOrder(
                paramCurrencyPair.getCurrencyPair(),
                BinanceAdapters.id(orderId),
                null,
                null);
      } else {
        super.cancelMarginOrder(
                paramCurrencyPair.getCurrencyPair(),
                marginAccountType,
                BinanceAdapters.id(orderId),
                null,
                null);
      }
      return true;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
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
          super.myTrades(pair, orderId, startTime, endTime, fromId, limit);
      List<UserTrade> trades =
          binanceTrades.stream()
              .map(
                  t ->
                      new UserTrade.Builder()
                          .type(BinanceAdapters.convertType(t.isBuyer))
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
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new BinanceTradeHistoryParams();
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
  public Class getRequiredOrderQueryParamClass() {
    return OrderQueryParamCurrencyPair.class;
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
        String orderId = orderQueryParamCurrencyPair.getOrderId();

        MarginAccountType marginAccountType =
                param instanceof BinanceQueryOrderParams ? ((BinanceQueryOrderParams) param).getMarginAccountType()
                        : BinanceAdapters.getMarginAccountTypeFromOrderId(orderId);

        BinanceOrder binanceOrder;
        if (marginAccountType == null) {
          binanceOrder = super.orderStatus(
                  orderQueryParamCurrencyPair.getCurrencyPair(),
                  BinanceAdapters.id(orderId),
                  null);
        } else {
          binanceOrder = super.marginOrderStatus(
                  orderQueryParamCurrencyPair.getCurrencyPair(),
                  marginAccountType,
                  BinanceAdapters.id(orderId),
                  null);
        }
        orders.add(BinanceAdapters.adaptOrder(binanceOrder, includeMarginAccountTypeInOrderId ? marginAccountType : null));
      }
      return orders;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
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
