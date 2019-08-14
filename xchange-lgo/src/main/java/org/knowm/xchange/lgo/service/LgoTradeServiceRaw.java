package org.knowm.xchange.lgo.service;

import java.io.IOException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.lgo.LgoAdapters;
import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.lgo.dto.WithCursor;
import org.knowm.xchange.lgo.dto.trade.LgoUserTrades;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;

public class LgoTradeServiceRaw extends LgoBaseService {

  protected LgoTradeServiceRaw(LgoExchange exchange) {
    super(exchange);
  }

  protected WithCursor<LgoUserTrades> getLastTrades(
      long nextLong,
      LgoSignatureService signatureService,
      CurrencyPair productId,
      Integer maxResults,
      String page,
      TradeHistoryParamsSorted.Order sort)
      throws IOException {
    return proxy.getLastTrades(
        nextLong,
        signatureService,
        LgoAdapters.adaptCurrencyPair(productId),
        maxResults,
        page,
        sort == null ? null : sort.name().toUpperCase());
  }
}
