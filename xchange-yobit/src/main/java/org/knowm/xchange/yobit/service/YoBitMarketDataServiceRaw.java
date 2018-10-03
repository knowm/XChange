package org.knowm.xchange.yobit.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.yobit.YoBit;
import org.knowm.xchange.yobit.YoBitAdapters;
import org.knowm.xchange.yobit.dto.marketdata.YoBitInfo;
import org.knowm.xchange.yobit.dto.marketdata.YoBitOrderBooksReturn;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTickersReturn;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTrades;

public class YoBitMarketDataServiceRaw extends YoBitBaseService<YoBit> {

  protected YoBitMarketDataServiceRaw(Exchange exchange) {
    super(YoBit.class, exchange);
  }

  public YoBitInfo getProducts() throws IOException {
    return service.getProducts();
  }

  // TODO error message handling http 414 (url too long)
  public YoBitTickersReturn getYoBitTickers(Iterable<CurrencyPair> currencyPairs)
      throws IOException {
    return service.getTickers(YoBitAdapters.adaptCcyPairsToUrlFormat(currencyPairs));
  }

  public YoBitOrderBooksReturn getOrderBooks(Iterable<CurrencyPair> currencyPairs, Integer limit)
      throws IOException {
    return service.getOrderBooks(YoBitAdapters.adaptCcyPairsToUrlFormat(currencyPairs), limit);
  }

  public YoBitTrades getPublicTrades(Iterable<CurrencyPair> currencyPairs) throws IOException {
    return service.getTrades(YoBitAdapters.adaptCcyPairsToUrlFormat(currencyPairs));
  }
}
