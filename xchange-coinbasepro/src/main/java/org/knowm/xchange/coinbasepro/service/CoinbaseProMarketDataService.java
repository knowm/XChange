package org.knowm.xchange.coinbasepro.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductStats;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoinbaseProMarketDataService extends CoinbaseProMarketDataServiceRaw
    implements MarketDataService {

  public CoinbaseProMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args)
      throws IOException, RateLimitExceededException {

    // Request data
    CoinbaseProProductTicker ticker = getCoinbaseProProductTicker(currencyPair);
    CoinbaseProProductStats stats = getCoinbaseProProductStats(currencyPair);

    // Adapt to XChange DTOs
    return CoinbaseProAdapters.adaptTicker(ticker, stats, currencyPair);
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

    return CoinbaseProAdapters.adaptOrderBook(
        getCoinbaseProProductOrderBook(currencyPair, level), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws IOException, RateLimitExceededException {

    return CoinbaseProAdapters.adaptTrades(getCoinbaseProTrades(currencyPair), currencyPair);
  }
}
