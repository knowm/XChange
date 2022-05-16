package org.knowm.xchange.bitrue.service;

import org.knowm.xchange.bitrue.BitrueAdapters;
import org.knowm.xchange.bitrue.BitrueAuthenticated;
import org.knowm.xchange.bitrue.BitrueExchange;
import org.knowm.xchange.bitrue.dto.marketdata.*;
import org.knowm.xchange.bitrue.dto.meta.BitrueTime;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.util.List;

import static org.knowm.xchange.bitrue.BitrueResilience.REQUEST_WEIGHT_RATE_LIMITER;

public class BitrueMarketDataServiceRaw extends BitrueBaseService {

  protected BitrueMarketDataServiceRaw(
      BitrueExchange exchange,
      BitrueAuthenticated bitrue,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, bitrue, resilienceRegistries);
  }

  public void ping() throws IOException {
    decorateApiCall(() -> bitrue.ping())
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public BitrueTime binanceTime() throws IOException {
    return decorateApiCall(() -> bitrue.time())
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public BitrueOrderbook getBitrueOrderbook(CurrencyPair pair, Integer limit) throws IOException {
    return decorateApiCall(() -> bitrue.depth(BitrueAdapters.toSymbol(pair), limit))
        .withRetry(retry("depth"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), depthPermits(limit))
        .call();
  }

  public List<BitrueAggTrades> aggTrades(
      CurrencyPair pair, Long fromId, Long startTime, Long endTime, Integer limit)
      throws IOException {
    return decorateApiCall(
            () ->
                bitrue.aggTrades(
                    BitrueAdapters.toSymbol(pair), fromId, startTime, endTime, limit))
        .withRetry(retry("aggTrades"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), aggTradesPermits(limit))
        .call();
  }

//  public BitrueKline lastKline(CurrencyPair pair, KlineInterval interval) throws IOException {
//    return klines(pair, interval, 1, null, null).stream().collect(StreamUtils.singletonCollector());
//  }
//
//  public List<BitrueKline> klines(CurrencyPair pair, KlineInterval interval) throws IOException {
//    return klines(pair, interval, null, null, null);
//  }
//
//
//
//  public List<BitrueTicker24h> ticker24h() throws IOException {
//    return decorateApiCall(() -> binance.ticker24h())
//        .withRetry(retry("ticker24h"))
//        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 40)
//        .call();
//  }

  public List<BitrueTicker24h> ticker24h(CurrencyPair pair) throws IOException {
    List<BitrueTicker24h> ticker24h =
        decorateApiCall(() -> bitrue.ticker24h(BitrueAdapters.toSymbol(pair)))
            .withRetry(retry("ticker24h"))
            .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
            .call();
    ticker24h.forEach(e -> e.setCurrencyPair(pair));
    return ticker24h;
  }

  public BitruePrice tickerPrice(CurrencyPair pair) throws IOException {
    return decorateApiCall(()-> bitrue.tickerPrice(BitrueAdapters.toSymbol(pair)))
            .withRetry(retry("tickerPrice"))
            .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER)).call();
  }

//  public List<BitruePrice> tickerAllPrices() throws IOException {
//    return decorateApiCall(() -> binance.tickerAllPrices())
//        .withRetry(retry("tickerAllPrices"))
//        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
//        .call();
//  }
//
//  public List<BitruePriceQuantity> tickerAllBookTickers() throws IOException {
//    return decorateApiCall(() -> binance.tickerAllBookTickers())
//        .withRetry(retry("tickerAllBookTickers"))
//        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
//        .call();
//  }

  protected int depthPermits(Integer limit) {
    if (limit == null || limit <= 100) {
      return 1;
    } else if (limit <= 500) {
      return 5;
    } else if (limit <= 1000) {
      return 10;
    }
    return 50;
  }

  protected int aggTradesPermits(Integer limit) {
    if (limit != null && limit > 500) {
      return 2;
    }
    return 1;
  }
}
