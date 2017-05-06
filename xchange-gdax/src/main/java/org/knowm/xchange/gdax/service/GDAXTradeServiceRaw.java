package org.knowm.xchange.gdax.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.gdax.GDAX;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;
import org.knowm.xchange.gdax.dto.trade.GDAXIdResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXPlaceOrder;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class GDAXTradeServiceRaw extends GDAXBaseService<GDAX> {

  public GDAXTradeServiceRaw(Exchange exchange) {

    super(GDAX.class, exchange);
  }

  public GDAXOrder[] getCoinbaseExOpenOrders() throws IOException {
    return coinbaseEx.getListOrders(apiKey, digest, getTimestamp(), passphrase, "open");
  }

  public GDAXFill[] getCoinbaseExFills(TradeHistoryParams tradeHistoryParams) throws IOException {
    String transactionId;
    if (tradeHistoryParams instanceof TradeHistoryParamTransactionId) {
      transactionId = ((TradeHistoryParamTransactionId) tradeHistoryParams).getTransactionId();
      if (transactionId == null || transactionId.isEmpty()) {
        transactionId = "all";
      }
    } else {
      transactionId = "all";
    }

    return coinbaseEx.getFills(apiKey, digest, getTimestamp(), passphrase, transactionId);
  }

  public GDAXIdResponse placeCoinbaseExLimitOrder(LimitOrder limitOrder) throws IOException {

    String side = limitOrder.getType().equals(OrderType.BID) ? "buy" : "sell";
    String productId = limitOrder.getCurrencyPair().base.getCurrencyCode() + "-" + limitOrder.getCurrencyPair().counter.getCurrencyCode();

    return coinbaseEx.placeLimitOrder(new GDAXPlaceOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), side, productId, "limit", limitOrder.getOrderFlags()),
        apiKey, digest, getTimestamp(), passphrase);
  }

  public GDAXIdResponse placeCoinbaseExMarketOrder(MarketOrder marketOrder) throws IOException {

    String side = marketOrder.getType().equals(OrderType.BID) ? "buy" : "sell";
    String productId = marketOrder.getCurrencyPair().base.getCurrencyCode() + "-" + marketOrder.getCurrencyPair().counter.getCurrencyCode();

    return coinbaseEx.placeMarketOrder(new GDAXPlaceOrder(marketOrder.getTradableAmount(), null, side, productId, "market", marketOrder.getOrderFlags()), apiKey, digest,
        getTimestamp(), passphrase);
  }

  public boolean cancelCoinbaseExOrder(String id) throws IOException {
    coinbaseEx.cancelOrder(id, apiKey, digest, getTimestamp(), passphrase);
    return true;
  }
}
