package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
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
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.utils.Assert;

public class BinanceTradeService extends BinanceTradeServiceRaw implements TradeService {

  public BinanceTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() {
    throw new ExchangeException("You need to provide the currency pair to get the list of open orders.");
  }

  public OpenOrders getOpenOrders(CurrencyPair pair) throws IOException {
    return getOpenOrders(new DefaultOpenOrdersParamCurrencyPair(pair));
  }
  
  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    Assert.isTrue(params instanceof OpenOrdersParamCurrencyPair, "You need to provide the currency pair to get the list of open orders.");
    OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
    CurrencyPair pair = pairParams.getCurrencyPair();
    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    List<BinanceOrder> binanceOpenOrders = super.openOrders(pair, recvWindow, getTimestamp());
    List<LimitOrder> openOrders = binanceOpenOrders.stream().map(o -> 
    new LimitOrder.Builder(BinanceAdapters.convert(o.side), pair)
      .id(Long.toString(o.orderId))
      .originalAmount(o.origQty)
      .cumulativeAmount(o.executedQty)
      .limitPrice(o.price)
      .timestamp(o.getTime())
      .build()
    ).collect(Collectors.toList());
    return new OpenOrders(openOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder mo) throws IOException {
    return placeOrder(OrderType.MARKET, mo, null, null, null);
  }

  @Override
  public String placeLimitOrder(LimitOrder lo) throws IOException {
    return placeOrder(OrderType.LIMIT, lo, lo.getLimitPrice(), null, TimeInForce.GTC);
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  private String placeOrder(OrderType type, Order order, BigDecimal limitPrice, BigDecimal stopPrice, TimeInForce tif) throws IOException {
    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    BinanceNewOrder newOrder = newOrder(order.getCurrencyPair(), BinanceAdapters.convert(order.getType()), type, tif, 
        order.getOriginalAmount(), limitPrice, null, stopPrice, null, recvWindow, getTimestamp());
    return Long.toString(newOrder.orderId);
  }

  public void placeTestOrder(OrderType type, Order order, BigDecimal limitPrice, BigDecimal stopPrice) throws IOException {
    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    testNewOrder(order.getCurrencyPair(), BinanceAdapters.convert(order.getType()), type, TimeInForce.GTC, 
        order.getOriginalAmount(), limitPrice, null, stopPrice, null, recvWindow, getTimestamp());
  }

  @Override
  public boolean cancelOrder(String orderId) {
    throw new ExchangeException("You need to provide the currency pair to cancel an order.");
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {
    if (!(params instanceof CancelOrderByCurrencyPair) && !(params instanceof CancelOrderByIdParams)) {
      throw new ExchangeException("You need to provide the currency pair and the order id to cancel an order.");
    }
    CancelOrderByCurrencyPair paramCurrencyPair = (CancelOrderByCurrencyPair) params;
    CancelOrderByIdParams paramId = (CancelOrderByIdParams) params;
    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    super.cancelOrder(paramCurrencyPair.getCurrencyPair(), BinanceAdapters.id(paramId.getOrderId()), null, null,
        recvWindow, getTimestamp());
    return true;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Assert.isTrue(params instanceof TradeHistoryParamCurrencyPair, "You need to provide the currency pair to get the user trades.");
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

    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    List<BinanceTrade> binanceTrades = super.myTrades(pair, limit, fromId, recvWindow, getTimestamp());
    List<UserTrade> trades = binanceTrades.stream()
        .map(t -> new UserTrade(BinanceAdapters.convertType(t.isBuyer), t.qty, pair, t.price, t.getTime()
            , Long.toString(t.id), Long.toString(t.orderId), t.commission, Currency.getInstance(t.commissionAsset)))
        .collect(Collectors.toList());
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
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

}
