package org.knowm.xchange.lgo.service;

import java.io.IOException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.lgo.LgoAdapters;
import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.lgo.dto.LgoException;
import org.knowm.xchange.lgo.dto.WithCursor;
import org.knowm.xchange.lgo.dto.order.LgoEncryptedOrder;
import org.knowm.xchange.lgo.dto.order.LgoPlaceOrderResponse;
import org.knowm.xchange.lgo.dto.order.LgoUnencryptedOrder;
import org.knowm.xchange.lgo.dto.trade.LgoUserTrades;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;

public class LgoTradeServiceRaw extends LgoBaseService {

  protected LgoTradeServiceRaw(LgoExchange exchange) {
    super(exchange);
  }

  protected WithCursor<LgoUserTrades> getLastTrades(
      CurrencyPair productId, Integer maxResults, String page, TradeHistoryParamsSorted.Order sort)
      throws IOException, LgoException {
    return proxy.getLastTrades(
        exchange.getNonceFactory().createValue(),
        exchange.getSignatureService(),
        LgoAdapters.adaptCurrencyPair(productId),
        maxResults,
        page,
        sort == null ? null : sort.name().toUpperCase());
  }

  protected String placeLgoEncryptedOrder(LgoEncryptedOrder lgoEncryptedOrder)
      throws IOException, LgoException {
    return proxy.placeEncryptedOrder(
            lgoEncryptedOrder,
            exchange.getNonceFactory().createValue(),
            exchange.getSignatureService())
        .orderId;
  }

  protected String placeLgoUnencryptedOrder(LgoUnencryptedOrder order)
      throws IOException, LgoException {
    LgoPlaceOrderResponse lgoPlaceOrderResponse =
        proxy.placeUnencryptedOrder(
            order, exchange.getNonceFactory().createValue(), exchange.getSignatureService());
    return lgoPlaceOrderResponse.orderId;
  }

  protected String placeLgoUnencryptedCancelOrder(String orderId) throws IOException, LgoException {
    LgoPlaceOrderResponse lgoPlaceOrderResponse =
        proxy.placeUnencryptedCancelOrder(
            exchange.getNonceFactory().createValue(), exchange.getSignatureService(), orderId);
    return lgoPlaceOrderResponse.orderId;
  }
}
