package org.knowm.xchange.kucoin;

import java.util.List;
import java.util.function.Supplier;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

import com.kucoin.sdk.exception.KucoinApiException;
import com.kucoin.sdk.rest.response.OrderBookResponse;
import com.kucoin.sdk.rest.response.SymbolResponse;
import com.kucoin.sdk.rest.response.SymbolTickResponse;
import com.kucoin.sdk.rest.response.TickerResponse;
import com.kucoin.sdk.rest.response.TradeHistoryResponse;

public class KucoinMarketDataServiceRaw extends KucoinBaseService {

  protected KucoinMarketDataServiceRaw(KucoinExchange exchange) {
    super(exchange);
  }

  public TickerResponse getKucoinTicker(CurrencyPair pair) {
    return classifyExceptions(() ->
      kucoinRestClient.symbolAPI().getTicker(KucoinAdapters.adaptCurrencyPair(pair)));
  }

  public SymbolTickResponse getKucoin24hrStats(CurrencyPair pair) {
    return classifyExceptions(() ->
      kucoinRestClient.symbolAPI().get24hrStats(KucoinAdapters.adaptCurrencyPair(pair)));
  }

  public List<SymbolResponse> getKucoinSymbols() {
    return classifyExceptions(() ->
      kucoinRestClient.symbolAPI().getSymbols());
  }

  public OrderBookResponse getKucoinOrderBookPartial(CurrencyPair pair) {
    return classifyExceptions(() ->
      kucoinRestClient.orderBookAPI().getPartOrderBookAggregated(
        KucoinAdapters.adaptCurrencyPair(pair)));
  }

  public OrderBookResponse getKucoinOrderBookFull(CurrencyPair pair) {
    return classifyExceptions(() ->
      kucoinRestClient.orderBookAPI().getFullOrderBookAggregated(
        KucoinAdapters.adaptCurrencyPair(pair)));
  }

  public List<TradeHistoryResponse> getKucoinTrades(CurrencyPair pair) {
    return classifyExceptions(() ->
      kucoinRestClient.historyAPI().getTradeHistories(
        KucoinAdapters.adaptCurrencyPair(pair)));
  }

  private <T> T classifyExceptions(Supplier<T> apiCall) {
    try {
      T result = apiCall.get();
      if (result == null) {
        throw new ExchangeException("Empty response from Kucoin. Check logs.");
      }
      return result;
    } catch (KucoinApiException e) {
      throw KucoinExceptionClassifier.classify(e);
    }
  }
}