package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.hitbtc.v2.HitbtcAdapters;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOwnTrade;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

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

    int count = 1000;
    int offset = 0;

    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      if (pagingParams.getPageLength() != null) {
        count = pagingParams.getPageLength();
      }

      Integer pageNumber = pagingParams.getPageNumber();
      offset = count * (pageNumber != null ? pageNumber : 0);
    }

    String symbols = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      TradeHistoryParamCurrencyPair tradeHistoryParamCurrencyPair = (TradeHistoryParamCurrencyPair) params;
      CurrencyPair pair = tradeHistoryParamCurrencyPair.getCurrencyPair();
      symbols = HitbtcAdapters.adaptCurrencyPair(pair);
    }

    List<HitbtcOwnTrade> tradeHistoryRaw = getTradeHistoryRaw(offset, count, symbols);
    return HitbtcAdapters.adaptTradeHistory(tradeHistoryRaw, exchange.getExchangeMetaData());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return HitbtcTradeHistoryParams.builder().build();
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