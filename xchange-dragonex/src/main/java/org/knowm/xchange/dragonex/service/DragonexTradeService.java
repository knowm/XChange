package org.knowm.xchange.dragonex.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dragonex.dto.trade.DealHistory;
import org.knowm.xchange.dragonex.dto.trade.DealHistoryRequest;
import org.knowm.xchange.dragonex.dto.trade.OrderHistory;
import org.knowm.xchange.dragonex.dto.trade.OrderHistoryRequest;
import org.knowm.xchange.dragonex.dto.trade.OrderPlacement;
import org.knowm.xchange.dragonex.dto.trade.OrderReference;
import org.knowm.xchange.dragonex.dto.trade.UserOrder;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted.Order;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class DragonexTradeService extends DragonexTradeServiceRaw implements TradeService {

  public DragonexTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    throw new ExchangeException("You need to provide the currency pair.");
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (!(params instanceof OpenOrdersParamCurrencyPair)) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
    if (pairParams.getCurrencyPair() == null) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    long symbolId = exchange.symbolId(pairParams.getCurrencyPair());
    OrderHistoryRequest req =
        new OrderHistoryRequest(symbolId, null, null, 1000, 1 /* pending status*/);
    OrderHistory orderHistory = super.orderHistory(exchange.getOrCreateToken().token, req);

    List<LimitOrder> openOrders =
        orderHistory.getList().stream()
            .map(
                o ->
                    new LimitOrder(
                        o.orderType == 1 ? OrderType.BID : OrderType.ASK,
                        o.volume,
                        exchange.pair(o.symbolId),
                        Long.toString(o.orderId),
                        o.getTimestamp(),
                        o.price))
            .collect(Collectors.toList());
    return new OpenOrders(openOrders);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    OrderPlacement orderPlacement =
        new OrderPlacement(
            exchange.symbolId(limitOrder.getCurrencyPair()),
            limitOrder.getLimitPrice(),
            limitOrder.getOriginalAmount());
    UserOrder newOrder =
        limitOrder.getType() == OrderType.BID
            ? super.orderBuy(exchange.getOrCreateToken().token, orderPlacement)
            : super.orderSell(exchange.getOrCreateToken().token, orderPlacement);
    return Long.toString(newOrder.orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {

    if (!(params instanceof CancelOrderByCurrencyPair)) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    if (!(params instanceof CancelOrderByIdParams)) {
      throw new ExchangeException("You need to provide the order id.");
    }

    CurrencyPair pair = ((CancelOrderByCurrencyPair) params).getCurrencyPair();
    if (pair == null) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    long orderId;
    try {
      orderId = Long.valueOf(((CancelOrderByIdParams) params).getOrderId());
    } catch (Throwable e) {
      throw new ExchangeException("You need to provide the order id as a number.", e);
    }
    OrderReference ref = new OrderReference(exchange.symbolId(pair), orderId);
    UserOrder order = super.orderCancel(exchange.getOrCreateToken().token, ref);
    return order.status == 3;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    if (!(params instanceof TradeHistoryParamCurrencyPair)) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    TradeHistoryParamCurrencyPair pairParams = (TradeHistoryParamCurrencyPair) params;
    CurrencyPair pair = pairParams.getCurrencyPair();
    if (pair == null) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    long symbolId = exchange.symbolId(pair);
    Integer direction = null;
    if (params instanceof TradeHistoryParamsSorted) {
      TradeHistoryParamsSorted sort = (TradeHistoryParamsSorted) params;
      direction = sort.getOrder() == Order.asc ? 1 : 2;
    }
    DealHistoryRequest req = new DealHistoryRequest(symbolId, direction, null, 1000);
    DealHistory dealHistory = super.dealHistory(exchange.getOrCreateToken().token, req);
    List<UserTrade> trades =
        dealHistory.getList().stream()
            .map(
                d -> {
                  CurrencyPair currencyPair = exchange.pair(d.symbolId);
                  return new UserTrade.Builder()
                      .type(d.orderType == 1 ? OrderType.BID : OrderType.ASK)
                      .originalAmount(d.volume)
                      .currencyPair(currencyPair)
                      .price(d.price)
                      .timestamp(d.getTimestamp())
                      .id(d.tradeId)
                      .orderId(d.orderId)
                      .feeAmount(d.charge)
                      .feeCurrency(currencyPair.counter)
                      .build();
                })
            .collect(Collectors.toList());
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamCurrencyPair();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }
}
