package org.knowm.xchange.coindirect.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.CoindirectAdapters;
import org.knowm.xchange.coindirect.dto.trade.CoindirectOrder;
import org.knowm.xchange.coindirect.dto.trade.CoindirectOrderRequest;
import org.knowm.xchange.coindirect.service.params.CoindirectTradeHistoryParams;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class CoindirectTradeService extends CoindirectTradeServiceRaw implements TradeService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public CoindirectTradeService(Exchange exchange) {
    super(exchange);
  }

  private List<CoindirectOrder> getOpenOrders(String symbol) throws IOException {
    return listExchangeOrders(symbol, false, 0, 1000);
  }

  private OpenOrders createOpenOrders(List<CoindirectOrder> coindirectOrders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    List<Order> otherOrders = new ArrayList<>();
    coindirectOrders.forEach(
        coindirectOrder -> {
          Order order = CoindirectAdapters.adaptOrder(coindirectOrder);
          if (order instanceof LimitOrder) {
            limitOrders.add((LimitOrder) order);
          } else {
            otherOrders.add(order);
          }
        });
    return new OpenOrders(limitOrders, otherOrders);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    List<CoindirectOrder> coindirectOrders = getOpenOrders((String) null);
    return createOpenOrders(coindirectOrders);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    String symbol = null;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
      symbol = CoindirectAdapters.toSymbol(pairParams.getCurrencyPair());
    }
    return createOpenOrders(getOpenOrders(symbol));
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    long fromOffset = 0;
    long max = 10;
    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan idParams = (TradeHistoryParamsIdSpan) params;

      try {
        fromOffset = Long.valueOf(idParams.getStartId());
      } catch (Throwable ignored) {
      }
    }

    List<CoindirectOrder> coindirectOrders = listExchangeOrders(null, true, fromOffset, max);

    List<UserTrade> trades =
        coindirectOrders.stream()
            .map(
                t -> {
                  if (t.executedAmount == null || t.executedAmount.signum() == 0) {
                    return null;
                  }
                  return new UserTrade.Builder()
                      .type(CoindirectAdapters.convert(t.side))
                      .originalAmount(t.executedAmount)
                      .currencyPair(CoindirectAdapters.toCurrencyPair(t.symbol))
                      .price(t.executedPrice)
                      .timestamp(t.dateCreated)
                      .id(t.uuid)
                      .orderId(t.uuid)
                      .feeAmount(t.executedFees)
                      .feeCurrency(CoindirectAdapters.toCurrencyPair(t.symbol).counter)
                      .build();
                })
            .filter(t -> t != null)
            .collect(Collectors.toList());

    return new UserTrades(trades, (fromOffset + max), Trades.TradeSortType.SortByTimestamp);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    CoindirectOrderRequest coindirectOrderRequest =
        new CoindirectOrderRequest(
            CoindirectAdapters.toSymbol(marketOrder.getCurrencyPair()),
            marketOrder.getOriginalAmount(),
            null,
            CoindirectOrder.Type.MARKET,
            CoindirectAdapters.convert(marketOrder.getType()));

    CoindirectOrder coindirectOrder = placeExchangeOrder(coindirectOrderRequest);

    return coindirectOrder.uuid;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    CoindirectOrderRequest coindirectOrderRequest =
        new CoindirectOrderRequest(
            CoindirectAdapters.toSymbol(limitOrder.getCurrencyPair()),
            limitOrder.getOriginalAmount(),
            limitOrder.getLimitPrice(),
            CoindirectOrder.Type.LIMIT,
            CoindirectAdapters.convert(limitOrder.getType()));

    CoindirectOrder coindirectOrder = placeExchangeOrder(coindirectOrderRequest);

    return coindirectOrder.uuid;
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    CoindirectOrder coindirectOrder = cancelExchangeOrder(orderId);
    switch (coindirectOrder.status) {
      case CANCELLED:
      case PENDING_CANCEL:
        return true;
    }
    return false;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    }

    throw new ExchangeException("You need to provide the the order id to cancel an order.");
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new CoindirectTradeHistoryParams();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    List<Order> orderList = new ArrayList<>();
    for (String orderId : orderIds) {
      orderList.add(CoindirectAdapters.adaptOrder(getExchangeOrder(orderId)));
    }
    return orderList;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    List<Order> orderList = new ArrayList<>();
    for (OrderQueryParams orderParams : orderQueryParams) {
      orderList.add(CoindirectAdapters.adaptOrder(getExchangeOrder(orderParams.getOrderId())));
    }
    return orderList;
  }
}
