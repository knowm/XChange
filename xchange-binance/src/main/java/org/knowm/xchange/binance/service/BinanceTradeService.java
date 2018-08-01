package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.BinanceNewOrder;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParam;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;
import org.knowm.xchange.utils.Assert;

public class BinanceTradeService extends BinanceTradeServiceRaw implements TradeService {

  public BinanceTradeService(Exchange exchange) {
    super(exchange);
  }

  public interface BinanceOrderFlags extends IOrderFlags {

    /** Used in fields 'newClientOrderId' */
    String getClientId();
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

    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");

    List<BinanceOrder> binanceOpenOrders;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
      CurrencyPair pair = pairParams.getCurrencyPair();
      binanceOpenOrders = super.openOrders(pair, recvWindow, getTimestamp());
    } else {
      binanceOpenOrders = super.openOrders(recvWindow, getTimestamp());
    }

    List<LimitOrder> limitOrders = new ArrayList<>();
    List<Order> otherOrders = new ArrayList<>();
    binanceOpenOrders.forEach(
        binanceOrder -> {
          Order order = BinanceAdapters.adaptOrder(binanceOrder);
          if (order instanceof LimitOrder) {
            limitOrders.add((LimitOrder) order);
          } else {
            otherOrders.add(order);
          }
        });
    return new OpenOrders(limitOrders, otherOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder mo) throws IOException {
    return placeOrder(OrderType.MARKET, mo, null, null, null);
  }

  @Override
  public String placeLimitOrder(LimitOrder lo) throws IOException {
    TimeInForce tif;
    Set<IOrderFlags> orderFlags = lo.getOrderFlags();
    if (orderFlags.size() == 1) {
      IOrderFlags orderFlag = orderFlags.iterator().next();
      Assert.isTrue(
          orderFlag instanceof TimeInForce, "Order flag should be instance of TimeInForce.");
      tif = (TimeInForce) orderFlag;
    } else {
      tif = TimeInForce.GTC;
    }
    return placeOrder(OrderType.LIMIT, lo, lo.getLimitPrice(), null, tif);
  }

  @Override
  public String placeStopOrder(StopOrder so) throws IOException {
    TimeInForce tif;
    Set<IOrderFlags> orderFlags = so.getOrderFlags();
    if (orderFlags.size() == 1) {
      IOrderFlags orderFlag = orderFlags.iterator().next();
      Assert.isTrue(
          orderFlag instanceof TimeInForce, "Order flag should be instance of TimeInForce.");
      tif = (TimeInForce) orderFlag;
    } else {
      tif = TimeInForce.GTC;
    }
    OrderType orderType;
    if (so.getType().equals(Order.OrderType.BID)) {
      orderType = so.getLimitPrice() == null ? OrderType.TAKE_PROFIT : OrderType.TAKE_PROFIT_LIMIT;
    } else {
      orderType = so.getLimitPrice() == null ? OrderType.STOP_LOSS : OrderType.STOP_LOSS_LIMIT;
    }
    return placeOrder(orderType, so, so.getLimitPrice(), so.getStopPrice(), tif);
  }

  private String placeOrder(
      OrderType type, Order order, BigDecimal limitPrice, BigDecimal stopPrice, TimeInForce tif)
      throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    BinanceNewOrder newOrder =
        newOrder(
            order.getCurrencyPair(),
            BinanceAdapters.convert(order.getType()),
            type,
            tif,
            order.getOriginalAmount(),
            limitPrice,
            getClientOrderId(order),
            stopPrice,
            null,
            recvWindow,
            getTimestamp());
    return Long.toString(newOrder.orderId);
  }

  public void placeTestOrder(
      OrderType type, Order order, BigDecimal limitPrice, BigDecimal stopPrice) throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    testNewOrder(
        order.getCurrencyPair(),
        BinanceAdapters.convert(order.getType()),
        type,
        TimeInForce.GTC,
        order.getOriginalAmount(),
        limitPrice,
        getClientOrderId(order),
        stopPrice,
        null,
        recvWindow,
        getTimestamp());
  }

  private String getClientOrderId(Order order) {
    String clientOrderId = null;
    for (IOrderFlags flags : order.getOrderFlags()) {
      if (flags instanceof BinanceOrderFlags) {
        BinanceOrderFlags bof = (BinanceOrderFlags) flags;
        if (clientOrderId == null) clientOrderId = bof.getClientId();
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
    if (!(params instanceof CancelOrderByCurrencyPair)
        && !(params instanceof CancelOrderByIdParams)) {
      throw new ExchangeException(
          "You need to provide the currency pair and the order id to cancel an order.");
    }
    CancelOrderByCurrencyPair paramCurrencyPair = (CancelOrderByCurrencyPair) params;
    CancelOrderByIdParams paramId = (CancelOrderByIdParams) params;
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    super.cancelOrder(
        paramCurrencyPair.getCurrencyPair(),
        BinanceAdapters.id(paramId.getOrderId()),
        null,
        null,
        recvWindow,
        getTimestamp());
    return true;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Assert.isTrue(
        params instanceof TradeHistoryParamCurrencyPair,
        "You need to provide the currency pair to get the user trades.");
    TradeHistoryParamCurrencyPair pairParams = (TradeHistoryParamCurrencyPair) params;
    CurrencyPair pair = pairParams.getCurrencyPair();
    if (pair == null) {
      throw new ExchangeException("You need to provide the currency pair to get the user trades.");
    }

    Integer limit = null;
    if (params instanceof TradeHistoryParamLimit) {
      TradeHistoryParamLimit limitParams = (TradeHistoryParamLimit) params;
      limit = limitParams.getLimit();
    }
    Long fromId = null;
    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan idParams = (TradeHistoryParamsIdSpan) params;

      try {
        fromId = BinanceAdapters.id(idParams.getStartId());
      } catch (Throwable ignored) {
      }
    }

    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    List<BinanceTrade> binanceTrades =
        super.myTrades(pair, limit, fromId, recvWindow, getTimestamp());
    List<UserTrade> trades =
        binanceTrades
            .stream()
            .map(
                t ->
                    new UserTrade(
                        BinanceAdapters.convertType(t.isBuyer),
                        t.qty,
                        pair,
                        t.price,
                        t.getTime(),
                        Long.toString(t.id),
                        Long.toString(t.orderId),
                        t.commission,
                        Currency.getInstance(t.commissionAsset)))
            .collect(Collectors.toList());
    long lastId = binanceTrades.stream().map(t -> t.id).max(Long::compareTo).orElse(0L);
    return new UserTrades(trades, lastId, Trades.TradeSortType.SortByTimestamp);
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
  public Collection<Order> getOrder(OrderQueryParams... params) throws IOException {
    Collection<Order> orders = new ArrayList<>();
    for (OrderQueryParams param : params) {
      if (!(param instanceof OrderQueryParamCurrencyPair)) {
        throw new ExchangeException(
            "Parameters must be an instance of OrderQueryParamCurrencyPair");
      }
      OrderQueryParamCurrencyPair orderQueryParamCurrencyPair = (OrderQueryParamCurrencyPair) param;
      if (orderQueryParamCurrencyPair.getCurrencyPair() == null
          || orderQueryParamCurrencyPair.getOrderId() == null) {
        throw new ExchangeException(
            "You need to provide the currency pair and the order id to query an order.");
      }

      orders.add(
          BinanceAdapters.adaptOrder(
              super.orderStatus(
                  orderQueryParamCurrencyPair.getCurrencyPair(),
                  BinanceAdapters.id(orderQueryParamCurrencyPair.getOrderId()),
                  null,
                  (Long)
                      exchange
                          .getExchangeSpecification()
                          .getExchangeSpecificParametersItem("recvWindow"),
                  getTimestamp())));
    }
    return orders;
  }
}
