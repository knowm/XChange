package org.knowm.xchange.krakenfutures.service;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.krakenfutures.KrakenFuturesAdapters;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesCancelAllOrders;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import jakarta.ws.rs.NotSupportedException;

/** @author Jean-Christophe Laruelle */
public class KrakenFuturesTradeService extends KrakenFuturesTradeServiceRaw
    implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenFuturesTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(null);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return KrakenFuturesAdapters.adaptOpenOrders(getKrakenFuturesOpenOrders());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeKrakenFuturesLimitOrder(limitOrder).getOrderId();
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[]{CancelOrderByIdParams.class};
  }

  @Override
  public OpenPositions getOpenPositions() throws IOException {
    return KrakenFuturesAdapters.adaptOpenPositions(getKrakenFuturesOpenPositions());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return placeKrakenFuturesMarketOrder(marketOrder).getOrderId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    return placeKrakenFuturesStopOrder(stopOrder).getOrderId();
  }

  @Override
  public String changeOrder(LimitOrder limitOrder) throws IOException {
    return changeKrakenFuturesOrder(limitOrder);
  }

  @Override
  public Collection<String> cancelAllOrders(CancelAllOrders orderParams) throws IOException {
    if(orderParams instanceof CancelOrderByInstrument){
      return cancelAllOrdersByInstrument(((CancelOrderByInstrument) orderParams).getInstrument()).getCancelStatus().getOrderIds().stream().map(KrakenFuturesCancelAllOrders.KrakenFuturesOrderId::getOrderId).collect(Collectors.toList());
    } else {
      throw new NotSupportedException("OrderParams need to implement CancelOrderByInstrument interface.");
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamInstrument();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    return getKrakenFuturesOrdersStatuses(orderIds).getOrders().stream().map(KrakenFuturesAdapters::adaptKrakenFuturesOrder)
            .collect(Collectors.toList());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelKrakenFuturesOrder(orderId).isSuccess();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      throw new NotSupportedException("CancelOrderParams need to be instance of CancelOrderByIdParams.");
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    return KrakenFuturesAdapters.adaptFills(getKrakenFuturesFills());
  }
}