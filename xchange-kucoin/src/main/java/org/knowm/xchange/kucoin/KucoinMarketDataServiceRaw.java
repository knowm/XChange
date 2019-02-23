package org.knowm.xchange.kucoin;

import static org.knowm.xchange.kucoin.KucoinExceptionClassifier.classifyingExceptions;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;

import com.kucoin.sdk.rest.response.OrderBookResponse;
import com.kucoin.sdk.rest.response.SymbolResponse;
import com.kucoin.sdk.rest.response.SymbolTickResponse;
import com.kucoin.sdk.rest.response.TickerResponse;
import com.kucoin.sdk.rest.response.TradeHistoryResponse;

public class KucoinMarketDataServiceRaw extends KucoinBaseService {

  protected KucoinMarketDataServiceRaw(KucoinExchange exchange) {
    super(exchange);
  }

  public TickerResponse getKucoinTicker(CurrencyPair pair) throws IOException {
    return classifyingExceptions(() ->
      kucoinRestClient.symbolAPI().getTicker(KucoinAdapters.adaptCurrencyPair(pair)));
  }

  public SymbolTickResponse getKucoin24hrStats(CurrencyPair pair) throws IOException {
    return classifyingExceptions(() ->
      kucoinRestClient.symbolAPI().get24hrStats(KucoinAdapters.adaptCurrencyPair(pair)));
  }

  public List<SymbolResponse> getKucoinSymbols() throws IOException {
    return classifyingExceptions(() ->
      kucoinRestClient.symbolAPI().getSymbols());
  }

  public OrderBookResponse getKucoinOrderBookPartial(CurrencyPair pair) throws IOException {
    return classifyingExceptions(() ->
      kucoinRestClient.orderBookAPI().getPartOrderBookAggregated(
        KucoinAdapters.adaptCurrencyPair(pair)));
  }

  public OrderBookResponse getKucoinOrderBookFull(CurrencyPair pair) throws IOException {
    return classifyingExceptions(() ->
      kucoinRestClient.orderBookAPI().getFullOrderBookAggregated(
        KucoinAdapters.adaptCurrencyPair(pair)));
  }

  public List<TradeHistoryResponse> getKucoinTrades(CurrencyPair pair) throws IOException {
    return classifyingExceptions(() ->
      kucoinRestClient.historyAPI().getTradeHistories(
        KucoinAdapters.adaptCurrencyPair(pair)));
  }
}