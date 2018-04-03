package org.knowm.xchange.gdax.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Created by Yingzhe on 4/6/2015. */
public class GDAXMarketDataService extends GDAXMarketDataServiceRaw implements MarketDataService {

  public GDAXMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args)
      throws IOException, RateLimitExceededException {

    // Request data
    GDAXProductTicker ticker = getGDAXProductTicker(currencyPair);
    GDAXProductStats stats = getGDAXProductStats(currencyPair);

    // Adapt to XChange DTOs
    return GDAXAdapters.adaptTicker(ticker, stats, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
      throws IOException, RateLimitExceededException {

    int level = 3; // full order book
    if (args != null && args.length > 0) {
      // parameter 1, if present, is the level
      if (args[0] instanceof Number) {
        Number arg = (Number) args[0];
        level = arg.intValue();
      } else {
        throw new IllegalArgumentException(
            "Extra argument #1, the 'level', must be an int (was " + args[0].getClass() + ")");
      }
    }

    return GDAXAdapters.adaptOrderBook(getGDAXProductOrderBook(currencyPair, level), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws IOException, RateLimitExceededException {

    return GDAXAdapters.adaptTrades(getGDAXTrades(currencyPair), currencyPair);
  }
}
