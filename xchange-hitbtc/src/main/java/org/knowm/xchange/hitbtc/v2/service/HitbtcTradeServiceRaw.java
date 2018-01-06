package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.hitbtc.v2.HitbtcAdapters;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOwnTrade;

public class HitbtcTradeServiceRaw extends HitbtcBaseService {

  public HitbtcTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<HitbtcOrder> getOpenOrdersRaw() throws IOException {

    return hitbtc.getHitbtcActiveOrders();
  }

  public HitbtcOrder placeMarketOrderRaw(MarketOrder marketOrder) throws IOException {

    String symbol = marketOrder.getCurrencyPair().base.getCurrencyCode() + marketOrder.getCurrencyPair().counter.getCurrencyCode();
    String side = HitbtcAdapters.getSide(marketOrder.getType()).toString();

    return hitbtc.postHitbtcNewOrder(null, symbol, side, null, marketOrder.getOriginalAmount(), "market", "IOC");
  }

  public HitbtcOrder placeLimitOrderRaw(LimitOrder limitOrder) throws IOException {

    String symbol = HitbtcAdapters.adaptCurrencyPair(limitOrder.getCurrencyPair());
    String side = HitbtcAdapters.getSide(limitOrder.getType()).toString();
    return hitbtc.postHitbtcNewOrder(null, symbol, side, limitOrder.getLimitPrice(), limitOrder.getOriginalAmount(), "limit", "GTC");
  }

  public HitbtcOrder updateMarketOrderRaw(String clientOrderId, BigDecimal quantity, String requestClientId, Optional<BigDecimal> price) throws IOException {

    return hitbtc.updateHitbtcOrder(clientOrderId, quantity, requestClientId, price.orElse(null));
  }

  public HitbtcOrder cancelOrderRaw(String clientOrderId) throws IOException {

    return hitbtc.cancelSingleOrder(clientOrderId);
  }

  public List<HitbtcOrder> cancelAllOrdersRaw(String symbol) throws IOException {

    return hitbtc.cancelAllOrders(symbol);
  }

  public List<HitbtcOwnTrade> getTradeHistoryRaw(int startIndex, int maxResults, String symbols) throws IOException {

    return hitbtc.getHitbtcTrades();
  }
  
  public HitbtcOrder getHitbtcOrder(String symbol, String orderId) throws IOException{
    List<HitbtcOrder> orders = hitbtc.getHitbtcOrder(symbol, orderId);
    
    if (orders == null || orders.size() == 0){
      return null;
    } else {
      return orders.iterator().next();
    }
  }
  

  public List<HitbtcBalance> getTradingBalance() throws IOException {

    return hitbtc.getTradingBalance();
  }
}