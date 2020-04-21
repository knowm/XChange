package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.livecoin.dto.marketdata.*;

public class LivecoinMarketDataServiceRaw extends LivecoinBaseService {

  public LivecoinMarketDataServiceRaw(
      LivecoinExchange exchange, Livecoin livecoin, ResilienceRegistries resilienceRegistries) {
    super(exchange, livecoin, resilienceRegistries);
  }

  public List<LivecoinRestriction> getRestrictions() throws IOException {
    return decorateApiCall(() -> service.getRestrictions().getRestrictions())
        .withRetry(retry("getRestrictions"))
        .call();
  }

  public LivecoinTicker getTickerRaw(CurrencyPair currencyPair) throws IOException {
    return decorateApiCall(
            () ->
                service.getTicker(
                    currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()))
        .withRetry(retry("getTicker"))
        .call();
  }

  public List<LivecoinTicker> getTickersRaw() throws IOException {
    return decorateApiCall(() -> service.getTicker()).withRetry(retry("getTicker")).call();
  }

  public LivecoinOrderBook getOrderBookRaw(
      CurrencyPair currencyPair, int depth, Boolean groupByPrice) throws IOException {
    return decorateApiCall(
            () ->
                service.getOrderBook(
                    currencyPair.base.getCurrencyCode().toUpperCase(),
                    currencyPair.counter.getCurrencyCode().toUpperCase(),
                    depth,
                    groupByPrice.toString()))
        .withRetry(retry("getOrderBook"))
        .call();
  }

  public Map<CurrencyPair, LivecoinOrderBook> getAllOrderBooksRaw(int depth, Boolean groupByPrice)
      throws IOException {
    LivecoinAllOrderBooks response =
        decorateApiCall(() -> service.getAllOrderBooks(depth, groupByPrice.toString()))
            .withRetry(retry("getAllOrderBooks"))
            .call();
    return LivecoinAdapters.adaptToCurrencyPairKeysMap(response);
  }

  public List<LivecoinTrade> getTradesRaw(CurrencyPair currencyPair) throws IOException {
    return decorateApiCall(
            () ->
                service.getTrades(
                    currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()))
        .withRetry(retry("getTrades"))
        .call();
  }
}
