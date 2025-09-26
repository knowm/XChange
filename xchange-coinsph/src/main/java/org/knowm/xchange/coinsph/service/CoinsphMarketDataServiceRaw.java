package org.knowm.xchange.coinsph.service;

import java.io.IOException;
import java.util.List; // For list responses like getTrades and getTicker24hr (all)
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.CoinsphAdapters;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.knowm.xchange.coinsph.dto.CoinsphException;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphOrderBook;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphPublicTrade;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphTicker;
import org.knowm.xchange.currency.CurrencyPair;

public class CoinsphMarketDataServiceRaw extends CoinsphBaseService {

  protected CoinsphMarketDataServiceRaw(
      CoinsphExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public CoinsphTicker getCoinsphTicker(CurrencyPair currencyPair)
      throws IOException, CoinsphException {
    return decorateApiCall(() -> coinsph.getTicker24hr(CoinsphAdapters.toSymbol(currencyPair)))
        .call();
  }

  public List<CoinsphTicker> getCoinsphTickers() throws IOException, CoinsphException {
    return decorateApiCall(() -> coinsph.getTicker24hr()).call();
  }

  public CoinsphOrderBook getCoinsphOrderBook(CurrencyPair currencyPair, Integer limit)
      throws IOException, CoinsphException {
    return decorateApiCall(
            () -> coinsph.getOrderBook(CoinsphAdapters.toSymbol(currencyPair), limit))
        .call();
  }

  public List<CoinsphPublicTrade> getCoinsphTrades(
      CurrencyPair currencyPair,
      Integer limit) // Coins.ph API uses 'limit', not 'fromId' for public trades
      throws IOException, CoinsphException {
    return decorateApiCall(() -> coinsph.getTrades(CoinsphAdapters.toSymbol(currencyPair), limit))
        .call();
  }
}
