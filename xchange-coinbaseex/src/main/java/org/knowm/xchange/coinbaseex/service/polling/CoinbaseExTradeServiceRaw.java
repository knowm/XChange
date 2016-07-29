package org.knowm.xchange.coinbaseex.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbaseex.CoinbaseEx;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExFill;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExIdResponse;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExOrder;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExPlaceOrder;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExTradeHistoryParams;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

public class CoinbaseExTradeServiceRaw extends CoinbaseExBasePollingService<CoinbaseEx> {

  public CoinbaseExTradeServiceRaw(Exchange exchange) {

    super(CoinbaseEx.class, exchange);
  }

  public CoinbaseExOrder[] getCoinbaseExOpenOrders() {
    return coinbaseEx.getListOrders(apiKey, digest, getTimestamp(), passphrase, "open");
  }

  public CoinbaseExFill[] getCoinbaseExFills(CoinbaseExTradeHistoryParams tradeHistoryParams) {
    return coinbaseEx.getFills(apiKey, digest, getTimestamp(), passphrase, tradeHistoryParams.getOrderId());
  }

  public CoinbaseExIdResponse placeCoinbaseExLimitOrder(LimitOrder limitOrder) {

    String side = limitOrder.getType().equals(OrderType.BID) ? "buy" : "sell";
    String productId = limitOrder.getCurrencyPair().base.getCurrencyCode() + "-" + limitOrder.getCurrencyPair().counter.getCurrencyCode();

    return coinbaseEx.placeLimitOrder(new CoinbaseExPlaceOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), side, productId, "limit"),
        apiKey, digest, getTimestamp(), passphrase);
  }

  public CoinbaseExIdResponse placeCoinbaseExMarketOrder(MarketOrder marketOrder) {

    String side = marketOrder.getType().equals(OrderType.BID) ? "buy" : "sell";
    String productId = marketOrder.getCurrencyPair().base.getCurrencyCode() + "-" + marketOrder.getCurrencyPair().counter.getCurrencyCode();

    return coinbaseEx.placeMarketOrder(new CoinbaseExPlaceOrder(marketOrder.getTradableAmount(), null, side, productId, "market"), apiKey, digest,
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
