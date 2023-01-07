package org.knowm.xchange.binance.service;

import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceFutureAuthenticated;
import org.knowm.xchange.binance.BinanceFutureExchange;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

import java.io.IOException;

import static org.knowm.xchange.binance.BinanceResilience.REQUEST_WEIGHT_RATE_LIMITER;

public class BinanceFutureMarketDataServiceRaw extends BinanceFutureBaseService {

  protected BinanceFutureMarketDataServiceRaw(
      BinanceFutureExchange exchange,
      BinanceFutureAuthenticated binance,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, binance, resilienceRegistries);
  }

  public BinanceOrderbook getBinanceOrderbook(Instrument pair, Integer limit) throws IOException {
    return decorateApiCall(
            () -> binance.depth(BinanceAdapters.toSymbol((CurrencyPair) pair), limit))
        .withRetry(retry("depth"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), depthPermits(limit))
        .call();
  }

  protected int depthPermits(Integer limit) {
    if (limit == null || limit <= 50) {
      return 2;
    } else if (limit <= 100) {
      return 5;
    } else if (limit <= 500) {
      return 10;
    }
    return 20;
  }
}
