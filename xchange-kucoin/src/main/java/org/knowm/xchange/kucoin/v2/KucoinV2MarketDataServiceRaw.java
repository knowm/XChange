package org.knowm.xchange.kucoin.v2;

import java.util.List;
import java.util.function.Supplier;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

import com.kucoin.sdk.exception.KucoinApiException;
import com.kucoin.sdk.rest.response.OrderBookResponse;
import com.kucoin.sdk.rest.response.SymbolResponse;
import com.kucoin.sdk.rest.response.SymbolTickResponse;
import com.kucoin.sdk.rest.response.TickerResponse;

public class KucoinV2MarketDataServiceRaw extends KucoinV2BaseService {

  protected KucoinV2MarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public TickerResponse getKucoinTicker(CurrencyPair pair) {
    return classifyExceptions(() ->
      kucoinRestClient.symbolAPI().getTicker(KucoinV2Adapters.adaptCurrencyPair(pair)));
  }

  public SymbolTickResponse getKucoin24hrStats(CurrencyPair pair) {
    return classifyExceptions(() ->
      kucoinRestClient.symbolAPI().get24hrStats(KucoinV2Adapters.adaptCurrencyPair(pair)));
  }

  public List<SymbolResponse> getKucoinSymbols() {
    return classifyExceptions(() ->
      kucoinRestClient.symbolAPI().getSymbols());
  }

  public OrderBookResponse getKucoinOrderBookPartial(CurrencyPair pair) {
    return classifyExceptions(() ->
      kucoinRestClient.orderBookAPI().getPartOrderBookAggregated(
        KucoinV2Adapters.adaptCurrencyPair(pair)));
  }

  public OrderBookResponse getKucoinOrderBookFull(CurrencyPair pair) {
    return classifyExceptions(() ->
      kucoinRestClient.orderBookAPI().getFullOrderBookAggregated(
        KucoinV2Adapters.adaptCurrencyPair(pair)));
  }

  private <T> T classifyExceptions(Supplier<T> apiCall) {
    try {
      T result = apiCall.get();
      if (result == null) {
        throw new ExchangeException("Empty response from Kucoin. Check logs.");
      }
      return result;
    } catch (KucoinApiException e) {
      throw KucoinV2ExceptionClassifier.classify(e);
    }
  }
}