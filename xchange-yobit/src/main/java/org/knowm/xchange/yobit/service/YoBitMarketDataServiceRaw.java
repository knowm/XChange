package org.knowm.xchange.yobit.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.yobit.YoBit;
import org.knowm.xchange.yobit.YoBitAdapters;
import org.knowm.xchange.yobit.dto.marketdata.YoBitInfo;
import org.knowm.xchange.yobit.dto.marketdata.YoBitOrderBooksReturn;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTickersReturn;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTrades;

public class YoBitMarketDataServiceRaw extends YoBitBaseService<YoBit> {

  // Not specified in the API doc but real
  private static final int MAX_PAIR_LIST_SIZE = 512;

  protected YoBitMarketDataServiceRaw(Exchange exchange) {
    super(YoBit.class, exchange);
  }

  public YoBitInfo getProducts() throws IOException {
    return service.getProducts();
  }

  public YoBitTickersReturn getYoBitTickers(Iterable<CurrencyPair> currencyPairs)
      throws IOException {
    return service.getTickers(getPairListAsString(currencyPairs));
  }

  public YoBitOrderBooksReturn getOrderBooks(Iterable<CurrencyPair> currencyPairs, Integer limit)
      throws IOException {
    return service.getOrderBooks(getPairListAsString(currencyPairs), limit);
  }

  private String getPairListAsString(Iterable<CurrencyPair> currencyPairs) {
    String markets = YoBitAdapters.adaptCcyPairsToUrlFormat(currencyPairs);
    if (markets.length() > MAX_PAIR_LIST_SIZE) {
      throw new ExchangeException("URL too long: YoBit allows a maximum of" + MAX_PAIR_LIST_SIZE + " characters for total pair lists size. Provided string is " + markets.length() + " characters long.");
    }
    return markets;
  }

  public YoBitTrades getPublicTrades(Iterable<CurrencyPair> currencyPairs) throws IOException {
    return service.getTrades(YoBitAdapters.adaptCcyPairsToUrlFormat(currencyPairs));
  }
}
