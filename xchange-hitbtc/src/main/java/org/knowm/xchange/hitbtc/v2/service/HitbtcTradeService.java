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
    return placeMarketOrderRaw(marketOrder).id;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeLimitOrderRaw(limitOrder).id;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      String clientOrderId = ((CancelOrderByIdParams) orderParams).getOrderId();
      HitbtcOrder cancelOrderRaw = cancelOrderRaw(clientOrderId);
      return "canceled".equals(cancelOrderRaw.status);
    } else {
      return false;
    }
  }

  /** Required parameters: {@link TradeHistoryParamPaging} {@link TradeHistoryParamCurrencyPair} */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    Integer limit = 1000;
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
    return HitbtcAdapters.adaptTradeHistory(tradeHistoryRaw);
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
    if (orderIds == null || orderIds.length == 0) {
      return new ArrayList<>();
    }

    Collection<Order> orders = new ArrayList<>();
    for (String orderId : orderIds) {
      HitbtcOrder rawOrder =
          getHitbtcOrder("BTCUSD", orderId); // why is the currency pair hardcoded?

      if (rawOrder != null) orders.add(HitbtcAdapters.adaptOrder(rawOrder));
    }

    return orders;
  }
}
