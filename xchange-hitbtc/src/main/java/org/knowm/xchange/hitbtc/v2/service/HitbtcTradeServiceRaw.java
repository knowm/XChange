package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.hitbtc.v2.HitbtcAdapters;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcLimitOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcMarketOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOwnTrade;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSort;

public class HitbtcTradeServiceRaw extends HitbtcBaseService {

  public HitbtcTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<HitbtcOrder> getOpenOrdersRaw() throws IOException {
    return hitbtc.getHitbtcActiveOrders();
  }

  public HitbtcOrder placeMarketOrderRaw(MarketOrder marketOrder) throws IOException {

    String symbol = HitbtcAdapters.adaptCurrencyPair(marketOrder.getCurrencyPair());
    String side = HitbtcAdapters.getSide(marketOrder.getType()).toString();

    String clientOrderId = null;
    if (marketOrder instanceof HitbtcMarketOrder) {
      clientOrderId = ((HitbtcMarketOrder) marketOrder).getClientOrderId();
    }

    return hitbtc.postHitbtcNewOrder(
        clientOrderId,
        symbol,
        side,
        null,
        marketOrder.getOriginalAmount(),
        HitbtcOrderType.market,
        HitbtcTimeInForce.IOC);
  }

  public HitbtcOrder placeLimitOrderRaw(LimitOrder limitOrder, HitbtcTimeInForce timeInForce)
      throws IOException {
    String symbol = HitbtcAdapters.adaptCurrencyPair(limitOrder.getCurrencyPair());
    String side = HitbtcAdapters.getSide(limitOrder.getType()).toString();

    String clientOrderId = null;
    if (limitOrder instanceof HitbtcLimitOrder) {
      HitbtcLimitOrder order = (HitbtcLimitOrder) limitOrder;
      clientOrderId = order.getClientOrderId();
    }

    return hitbtc.postHitbtcNewOrder(
        clientOrderId,
        symbol,
        side,
        limitOrder.getLimitPrice(),
        limitOrder.getOriginalAmount(),
        HitbtcOrderType.limit,
        timeInForce);
  }

  public HitbtcOrder placeLimitOrderRaw(LimitOrder limitOrder) throws IOException {
    return placeLimitOrderRaw(limitOrder, HitbtcTimeInForce.GTC);
  }

  public HitbtcOrder updateMarketOrderRaw(
      String clientOrderId, BigDecimal quantity, String requestClientId, Optional<BigDecimal> price)
      throws IOException {

    return hitbtc.updateHitbtcOrder(clientOrderId, quantity, requestClientId, price.orElse(null));
  }

  public HitbtcOrder cancelOrderRaw(String clientOrderId) throws IOException {
    return hitbtc.cancelSingleOrder(clientOrderId);
  }

  public List<HitbtcOrder> cancelAllOrdersRaw(String symbol) throws IOException {
    return hitbtc.cancelAllOrders(symbol);
  }

  public List<HitbtcOwnTrade> getHistorialTradesByOrder(String orderId) throws IOException {
    return hitbtc.getHistorialTradesByOrder(orderId);
  }

  public List<HitbtcOrder> getHitbtcRecentOrders() throws IOException {
    return hitbtc.getHitbtcRecentOrders();
  }

  public List<HitbtcOwnTrade> getTradeHistoryRaw(String symbol, Integer limit, long offset)
      throws IOException {
    return hitbtc.getHitbtcTrades(symbol, null, null, null, null, limit, offset);
  }

  public List<HitbtcOwnTrade> getTradeHistoryRaw(
      String symbol, HitbtcSort sort, Date from, Date till, Integer limit, long offset)
      throws IOException {
    String sortValue = sort != null ? sort.toString().toUpperCase() : null;
    String fromValue = from != null ? Instant.ofEpochMilli(from.getTime()).toString() : null;
    String tillValue = till != null ? Instant.ofEpochMilli(till.getTime()).toString() : null;
    return hitbtc.getHitbtcTrades(
        symbol, sortValue, "timestamp", fromValue, tillValue, limit, offset);
  }

  public List<HitbtcOwnTrade> getTradeHistoryRaw(
      String symbol, HitbtcSort sort, Long fromId, Date tillId, Integer limit, long offset)
      throws IOException {
    String sortValue = sort != null ? sort.toString().toUpperCase() : null;
    String fromValue = fromId != null ? fromId.toString() : null;
    String tillValue = tillId != null ? tillId.toString() : null;
    return hitbtc.getHitbtcTrades(symbol, sortValue, "id", fromValue, tillValue, limit, offset);
  }

  public HitbtcOrder getHitbtcOrder(String symbol, String clientOrderId) throws IOException {
    List<HitbtcOrder> orders = hitbtc.getHitbtcOrder(symbol, clientOrderId);

    if (orders == null || orders.size() == 0) {
      return null;
    } else {
      return orders.iterator().next();
    }
  }

  public List<HitbtcBalance> getTradingBalance() throws IOException {

    return hitbtc.getTradingBalance();
  }
}
