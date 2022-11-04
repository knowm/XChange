package org.knowm.xchange.binance.service;

import static org.knowm.xchange.binance.BinanceResilience.REQUEST_WEIGHT_RATE_LIMITER;

import java.io.IOException;
import org.knowm.xchange.binance.*;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.meta.BinanceTime;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;

public class BinanceFutureMarketDataServiceRaw extends BinanceFutureBaseService {

  protected BinanceFutureMarketDataServiceRaw(
      BinanceFutureExchange exchange,
      BinanceFutureAuthenticated binance,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, binance, resilienceRegistries);
  }

  public void ping() throws IOException {
    decorateApiCall(() -> binance.ping())
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public BinanceTime binanceTime() throws IOException {
    return decorateApiCall(() -> binance.time())
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public BinanceOrderbook getBinanceOrderbook(CurrencyPair pair, Integer limit) throws IOException {
    return decorateApiCall(() -> binance.depth(BinanceAdapters.toSymbol(pair), limit))
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
