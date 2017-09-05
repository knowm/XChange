package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOwnTrade;
import org.knowm.xchange.hitbtc.v2.internal.HitbtcAdapters;

public class HitbtcTradeServiceRaw extends HitbtcBaseService {

  public HitbtcTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<HitbtcOrder> getOpenOrdersRaw() throws IOException {

    return hitbtc.getHitbtcActiveOrders();
  }

  public HitbtcExecutionReportResponse placeMarketOrderRawBaseResponse(MarketOrder marketOrder) throws IOException {

    String symbol = marketOrder.getCurrencyPair().base.getCurrencyCode() + marketOrder.getCurrencyPair().counter.getCurrencyCode();

    long nonce = exchange.getNonceFactory().createValue();
    String side = HitbtcAdapters.getSide(marketOrder.getType()).toString();
    String orderId = HitbtcAdapters.createOrderId(marketOrder, nonce);

    return hitbtc.postHitbtcNewOrder(orderId, symbol, side, null, marketOrder.getTradableAmount(), "market", "IOC");
  }

  public HitbtcExecutionReport placeMarketOrderRaw(MarketOrder marketOrder) throws IOException {

    HitbtcExecutionReportResponse response = placeMarketOrderRawBaseResponse(marketOrder);
    return response.getExecutionReport();
  }

  public HitbtcExecutionReport placeLimitOrderRaw(LimitOrder limitOrder) throws IOException {

    HitbtcExecutionReportResponse postHitbtcNewOrder = fillHitbtcExecutionReportResponse(limitOrder);
    return postHitbtcNewOrder.getExecutionReport();
  }

  private HitbtcExecutionReportResponse fillHitbtcExecutionReportResponse(LimitOrder limitOrder) throws IOException {

    String symbol = HitbtcAdapters.adaptCurrencyPair(limitOrder.getCurrencyPair());
    long nonce = exchange.getNonceFactory().createValue();
    String side = HitbtcAdapters.getSide(limitOrder.getType()).toString();
    String orderId = HitbtcAdapters.createOrderId(limitOrder, nonce);

    return hitbtc.postHitbtcNewOrder(orderId, symbol, side, limitOrder.getLimitPrice(), limitOrder.getTradableAmount(), "limit", "GTC");

  }

  public HitbtcExecutionReportResponse cancelOrderRaw(String clientOrderId) throws IOException {

    return hitbtc.cancelSingleOrder(clientOrderId);
  }

  public List<HitbtcOrder> cancelAllOrdersRaw(String symbol) throws IOException {

    return hitbtc.cancelAllOrders(symbol);
  }

  public List<HitbtcOwnTrade> getTradeHistoryRaw(int startIndex, int maxResults, String symbols) throws IOException {

    return hitbtc.getHitbtcTrades();
  }

  public List<HitbtcBalance> getTradingBalance() throws IOException {
    return hitbtc.getTradingBalance();
  }

}
