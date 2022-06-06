package org.knowm.xchange.kucoin;

import static org.knowm.xchange.kucoin.KucoinExceptionClassifier.classifyingExceptions;
import static org.knowm.xchange.kucoin.KucoinResilience.PRIVATE_REST_ENDPOINT_RATE_LIMITER;
import static org.knowm.xchange.kucoin.KucoinResilience.PUBLIC_REST_ENDPOINT_RATE_LIMITER;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kucoin.dto.KlineIntervalType;
import org.knowm.xchange.kucoin.dto.response.AllTickersResponse;
import org.knowm.xchange.kucoin.dto.response.CurrenciesResponse;
import org.knowm.xchange.kucoin.dto.response.KucoinKline;
import org.knowm.xchange.kucoin.dto.response.OrderBookResponse;
import org.knowm.xchange.kucoin.dto.response.SymbolResponse;
import org.knowm.xchange.kucoin.dto.response.SymbolTickResponse;
import org.knowm.xchange.kucoin.dto.response.TickerResponse;
import org.knowm.xchange.kucoin.dto.response.TradeFeeResponse;
import org.knowm.xchange.kucoin.dto.response.TradeHistoryResponse;

public class KucoinMarketDataServiceRaw extends KucoinBaseService {

  protected KucoinMarketDataServiceRaw(
      KucoinExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public TickerResponse getKucoinTicker(CurrencyPair pair) throws IOException {
    return classifyingExceptions(
        () ->
            decorateApiCall(() -> symbolApi.getTicker(KucoinAdapters.adaptCurrencyPair(pair)))
                .withRetry(retry("ticker"))
                .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public AllTickersResponse getKucoinTickers() throws IOException {
    return classifyingExceptions(
        () ->
            decorateApiCall(symbolApi::getTickers)
                .withRetry(retry("tickers"))
                .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public SymbolTickResponse getKucoin24hrStats(CurrencyPair pair) throws IOException {
    return classifyingExceptions(
        () ->
            decorateApiCall(() -> symbolApi.getMarketStats(KucoinAdapters.adaptCurrencyPair(pair)))
                .withRetry(retry("24hrStats"))
                .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public Map<String, BigDecimal> getKucoinPrices() throws IOException {
    return classifyingExceptions(
        () ->
            decorateApiCall(symbolApi::getPrices)
                .withRetry(retry("prices"))
                .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public TradeFeeResponse getKucoinBaseFee() throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () ->
            decorateApiCall(
                    () -> tradingFeeAPI.getBaseFee(apiKey, digest, nonceFactory, passphrase))
                .withRetry(retry("baseFee"))
                .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public List<TradeFeeResponse> getKucoinTradeFee(String symbols) throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () ->
            decorateApiCall(
                    () ->
                        tradingFeeAPI.getTradeFee(
                            apiKey, digest, nonceFactory, passphrase, symbols))
                .withRetry(retry("tradeFee"))
                .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public List<SymbolResponse> getKucoinSymbols() throws IOException {
    return classifyingExceptions(
        () ->
            decorateApiCall(symbolApi::getSymbols)
                .withRetry(retry("symbols"))
                .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public List<CurrenciesResponse> getKucoinCurrencies() throws IOException {
    return classifyingExceptions(
        () ->
            decorateApiCall(symbolApi::getCurrencies)
                .withRetry(retry("currencies"))
                .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public OrderBookResponse getKucoinOrderBookPartial(CurrencyPair pair) throws IOException {
    return classifyingExceptions(
        () ->
            decorateApiCall(
                    () ->
                        orderBookApi.getPartOrderBookAggregated(
                            KucoinAdapters.adaptCurrencyPair(pair)))
                .withRetry(retry("partialOrderBook"))
                .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public OrderBookResponse getKucoinOrderBookPartialShallow(CurrencyPair pair) throws IOException {
    return classifyingExceptions(
        () ->
            decorateApiCall(
                    () ->
                        orderBookApi.getPartOrderBookShallowAggregated(
                            KucoinAdapters.adaptCurrencyPair(pair)))
                .withRetry(retry("partialShallowOrderBook"))
                .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public OrderBookResponse getKucoinOrderBookFull(CurrencyPair pair) throws IOException {
    return classifyingExceptions(
        () ->
            decorateApiCall(
                    () ->
                        orderBookApi.getFullOrderBookAggregated(
                            KucoinAdapters.adaptCurrencyPair(pair),
                            apiKey,
                            digest,
                            nonceFactory,
                            passphrase))
                .withRetry(retry("fullOrderBook"))
                .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public List<TradeHistoryResponse> getKucoinTrades(CurrencyPair pair) throws IOException {
    return classifyingExceptions(
        () ->
            decorateApiCall(
                    () -> historyApi.getTradeHistories(KucoinAdapters.adaptCurrencyPair(pair)))
                .withRetry(retry("tradeHistories"))
                .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
                .call());
  }

  public List<KucoinKline> getKucoinKlines(
      CurrencyPair pair, Long startTime, Long endTime, KlineIntervalType type) throws IOException {
    List<Object[]> raw =
        classifyingExceptions(
            () ->
                decorateApiCall(
                        () ->
                            historyApi.getKlines(
                                KucoinAdapters.adaptCurrencyPair(pair),
                                startTime,
                                endTime,
                                type.code()))
                    .withRetry(retry("klines"))
                    .withRateLimiter(rateLimiter(PUBLIC_REST_ENDPOINT_RATE_LIMITER))
                    .call());

    return raw.stream().map(obj -> new KucoinKline(pair, type, obj)).collect(Collectors.toList());
  }
}
