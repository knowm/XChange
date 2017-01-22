package org.knowm.xchange.gdax.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.gdax.GDAX;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;
import org.knowm.xchange.gdax.dto.trade.GDAXIdResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXPlaceOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXTradeHistoryParams;

public class GDAXTradeServiceRaw extends GDAXBaseService<GDAX> {

  public GDAXTradeServiceRaw(Exchange exchange) {

    super(GDAX.class, exchange);
  }

  public GDAXOrder[] getCoinbaseExOpenOrders() {
    return coinbaseEx.getListOrders(apiKey, digest, getTimestamp(), passphrase, "open");
  }

  public GDAXFill[] getCoinbaseExFills(GDAXTradeHistoryParams tradeHistoryParams) {
    return coinbaseEx.getFills(apiKey, digest, getTimestamp(), passphrase, tradeHistoryParams.getOrderId());
  }

  public GDAXIdResponse placeCoinbaseExLimitOrder(LimitOrder limitOrder) {

    String side = limitOrder.getType().equals(OrderType.BID) ? "buy" : "sell";
    String productId = limitOrder.getCurrencyPair().base.getCurrencyCode() + "-" + limitOrder.getCurrencyPair().counter.getCurrencyCode();

    return coinbaseEx.placeLimitOrder(new GDAXPlaceOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), side, productId, "limit"),
        apiKey, digest, getTimestamp(), passphrase);
  }

  public GDAXIdResponse placeCoinbaseExMarketOrder(MarketOrder marketOrder) {

    String side = marketOrder.getType().equals(OrderType.BID) ? "buy" : "sell";
    String productId = marketOrder.getCurrencyPair().base.getCurrencyCode() + "-" + marketOrder.getCurrencyPair().counter.getCurrencyCode();

    return coinbaseEx.placeMarketOrder(new GDAXPlaceOrder(marketOrder.getTradableAmount(), null, side, productId, "market"), apiKey, digest,
        getTimestamp(), passphrase);
  }

  public boolean cancelCoinbaseExOrder(String id) {
    try {
      coinbaseEx.cancelOrder(id, apiKey, digest, getTimestamp(), passphrase);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
