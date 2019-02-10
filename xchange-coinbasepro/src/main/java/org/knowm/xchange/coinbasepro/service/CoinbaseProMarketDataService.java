package org.knowm.xchange.coinbasepro.service;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTrades;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductStats;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.marketdata.MarketDataService;

@Slf4j
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

      return CoinbaseProAdapters.adaptTrades(getCoinbaseProTrades(currencyPair), currencyPair);

    } else if ((args.length == 2) && (args[0] instanceof Long) && (args[1] instanceof Long)) {

      Long fromTradeId = (Long) args[0];
      Long toTradeId = (Long) args[1];
      log.debug("fromTradeId: {}, toTradeId: {}", fromTradeId, toTradeId);
      Long latestTradeId = toTradeId;
      CoinbaseProTrades CoinbaseProTrades = new CoinbaseProTrades();
      for (; ; ) {
        CoinbaseProTrades CoinbaseProTradesNew =
            getCoinbaseProTradesExtended(currencyPair, latestTradeId, 100);
        CoinbaseProTrades.addAll(CoinbaseProTradesNew);
        log.debug(
            "latestTradeId: {}, earliest-latest: {}-{}, trades: {}",
            latestTradeId,
            CoinbaseProTrades.getEarliestTradeId(),
            CoinbaseProTrades.getLatestTradeId(),
            CoinbaseProTrades);
        latestTradeId = CoinbaseProTrades.getEarliestTradeId();

        if (CoinbaseProTradesNew.getEarliestTradeId() == null) {
          break;
        }

        if (CoinbaseProTrades.getEarliestTradeId() <= fromTradeId) {
          break;
        }
      }
      log.debug(
          "earliest-latest: {}-{}",
          CoinbaseProTrades.getEarliestTradeId(),
          CoinbaseProTrades.getLatestTradeId());

      if (log.isDebugEnabled()) {
        CoinbaseProTrades.stream().forEach(System.out::println);
      }

      return CoinbaseProAdapters.adaptTrades(CoinbaseProTrades, currencyPair);
    }

    throw new IllegalArgumentException("Invalid arguments passed to getTrades");
  }
}
