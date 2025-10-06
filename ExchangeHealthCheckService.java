package org.knowm.xchange.service.utils;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.time.Duration;

/**
 * Utility service to perform a lightweight health check on an Exchange.
 * Useful for bots, uptime monitors, and dashboards.
 *
 * Example:
 *   Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class);
 *   boolean isHealthy = ExchangeHealthCheckService.isExchangeHealthy(bitstamp, Duration.ofSeconds(3));
 */
public final class ExchangeHealthCheckService {

  private ExchangeHealthCheckService() {}

  /**
   * Pings the exchange via a simple ticker call.
   *
   * @param exchange Exchange instance
   * @param timeout Duration in which the response should arrive
   * @return true if the exchange responded successfully within the timeout
   */
  public static boolean isExchangeHealthy(Exchange exchange, Duration timeout) {
    MarketDataService marketDataService = exchange.getMarketDataService();
    long start = System.currentTimeMillis();
    try {
      Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
      long elapsed = System.currentTimeMillis() - start;
      return ticker != null && elapsed <= timeout.toMillis();
    } catch (Exception e) {
      return false;
    }
  }
}
