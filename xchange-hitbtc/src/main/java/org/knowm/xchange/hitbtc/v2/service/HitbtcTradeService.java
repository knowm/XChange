package org.knowm.xchange.hitbtc.v2.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.hitbtc.v2.HitbtcAdapters;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOwnTrade;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HitbtcTradeService extends HitbtcTradeServiceRaw implements TradeService {

  public HitbtcTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    List<HitbtcOrder> openOrdersRaw = getOpenOrdersRaw();
    return HitbtcAdapters.adaptOpenOrders(openOrdersRaw);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return placeMarketOrderRaw(marketOrder).clientOrderId;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeLimitOrderRaw(limitOrder).clientOrderId;
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    HitbtcOrder cancelOrderRaw = cancelOrderRaw(orderId);
    return "canceled".equals(cancelOrderRaw.status);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /**
   * Required parameters: {@link TradeHistoryParamPaging} {@link TradeHistoryParamCurrencyPair}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    long limit = 1000;
    long offset = 0;

    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    if (params instanceof TradeHistoryParamOffset) {
      TradeHistoryParamOffset tradeHistoryParamOffset = (TradeHistoryParamOffset) params;
      offset = tradeHistoryParamOffset.getOffset();
    }

    String symbol = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      symbol = HitbtcAdapters.adaptCurrencyPair(pair);
    }

    List<HitbtcOwnTrade> tradeHistoryRaw = getTradeHistoryRaw(symbol, limit, offset);
    return HitbtcAdapters.adaptTradeHistory(tradeHistoryRaw, exchange.getExchangeMetaData());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new HitbtcTradeHistoryParams(null, 100, 0L);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    if (orderIds == null || orderIds.length == 0){
      return new ArrayList<>();
    }

    Collection<Order> orders = new ArrayList<>();
    for (String orderId : orderIds) {
      HitbtcOrder rawOrder = getHitbtcOrder("BTCUSD", orderId);
      
      if (rawOrder != null)
        orders.add(HitbtcAdapters.adaptOrder(rawOrder));
    }
    
    return orders;
  }

  @Override
  public void verifyOrder(LimitOrder limitOrder) {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) {

    throw new NotYetImplementedForExchangeException();
  }
}