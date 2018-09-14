package org.knowm.xchange.gdax.service;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.GDAXTrades;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

@Deprecated // Please use module xchange-coinbasepro
@Slf4j
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

  /**
   * Get trades data for a specific currency pair
   *
   * <p>If invoked with only the currency pair, the method will make a single api call, returning
   * the default number (currently 100) of the most recent trades. If invoked with either optional
   * argument the other must be specified as well.
   *
   * @param currencyPair Currency pair to obtain trades for (required)
   * @param args[0] fromTradeId (Long) Return Trades with tradeIds greater than or equal to this
   *     value. Additional values may be returned. (optional)
   * @param args[1] toTradeId (Long) Return Trades with tradeIds up to but not including this value
   *     (optional)
   * @return A Trades object holding the requested trades
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws IOException, RateLimitExceededException {

    if (args.length == 0) {
      return GDAXAdapters.adaptTrades(getGDAXTrades(currencyPair), currencyPair);

      //	  } else if (args.length == 1 && args[0] instanceof Long){
      //		  GDAXTrades gdaxTrades = getGDAXTradesExtended(currencyPair, (Long)args[0], null);
      //
      //		  return GDAXAdapters.adaptTrades(gdaxTrades, currencyPair);

    } else if (args.length == 2 && args[0] instanceof Long && args[1] instanceof Long) {
      Long fromTradeId = (Long) args[0];
      Long toTradeId = (Long) args[1];

      log.debug("fromTradeId: {}, toTradeid: {}", fromTradeId, toTradeId);

      Long latestTradeId = toTradeId;

      GDAXTrades gdaxTrades = new GDAXTrades();

      for (; ; ) {
        GDAXTrades gdaxTradesNew = getGDAXTradesExtended(currencyPair, latestTradeId, 100);
        gdaxTrades.addAll(gdaxTradesNew);

        log.debug(
            "latestTradeId: {}, earliest-latest: {}-{}, trades: {}",
            latestTradeId,
            gdaxTrades.getEarliestTradeId(),
            gdaxTrades.getLatestTradeId(),
            gdaxTrades);

        latestTradeId = gdaxTrades.getEarliestTradeId();

        if (gdaxTradesNew.getEarliestTradeId() == null) {
          break;
        }
        if (gdaxTrades.getEarliestTradeId() <= fromTradeId) {
          break;
        }
      }

      log.debug(
          "earliest-latest: {}-{}", gdaxTrades.getEarliestTradeId(), gdaxTrades.getLatestTradeId());

      if (log.isDebugEnabled()) {
        gdaxTrades.stream().forEach(System.out::println);
      }

      return GDAXAdapters.adaptTrades(gdaxTrades, currencyPair);

    } else {
      throw new IllegalArgumentException("Invalid arguments passed to getTrades");
    }
  }
}
