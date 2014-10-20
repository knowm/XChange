package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Implementation of the market data service for BTCChina.
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 *
 * @author ObsessiveOrange
 */
public class BTCChinaMarketDataService extends BTCChinaMarketDataServiceRaw implements PollingMarketDataService {

  private final Logger log = LoggerFactory.getLogger(BTCChinaMarketDataService.class);

  /**
   * Constructor
   *
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCChinaMarketDataService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> tonceFactory) {

    super(exchangeSpecification, tonceFactory);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    BTCChinaTicker btcChinaTicker = getBTCChinaTicker(BTCChinaAdapters.adaptMarket(currencyPair));

    // Adapt to XChange DTOs
    return BTCChinaAdapters.adaptTicker(btcChinaTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    final BTCChinaDepth btcChinaDepth;
    final String market = BTCChinaAdapters.adaptMarket(currencyPair);

    if (args.length == 0) {
      btcChinaDepth = getBTCChinaOrderBook(market);
    }
    else {
      int limit = ((Number) args[0]).intValue();
      btcChinaDepth = getBTCChinaOrderBook(market, limit);
    }

    // Adapt to XChange DTOs
    return BTCChinaAdapters.adaptOrderBook(btcChinaDepth, currencyPair);
  }

  /**
   * Get the trades recently performed by the exchange.
   *
   * @param currencyPair market symbol.
   * @param args 2 arguments:
   *          <ol>
   *          <li>the starting trade ID(exclusive), null means the latest trades;</li>
   *          <li>the limit(number of records fetched, the range is [0,5000]), default is 100.</li>
   *          <ol>
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    final String market = BTCChinaAdapters.adaptMarket(currencyPair);
    final Number since = args.length > 0 ? (Number) args[0] : null;
    final Number limit = args.length > 1 ? (Number) args[1] : null;
    final String sinceType = args.length > 2 ? (String) args[2] : null;

    log.debug("market: {}, since: {}, limit: {}, sinceType: {}", market, since, limit, sinceType);

    final BTCChinaTrade[] btcChinaTrades;

    if (since != null && limit != null && sinceType != null) {
      btcChinaTrades = getBTCChinaHistoryData(market, since.longValue(), limit.intValue(), sinceType);
    }
    else if (since != null && limit != null) {
      btcChinaTrades = getBTCChinaHistoryData(market, since.longValue(), limit.intValue());
    }
    else if (since != null) {
      btcChinaTrades = getBTCChinaHistoryData(market, since.longValue());
    }
    else if (limit != null) {
      btcChinaTrades = getBTCChinaHistoryData(market, limit.intValue());
    }
    else {
      btcChinaTrades = getBTCChinaHistoryData(market);
    }

    // Adapt to XChange DTOs
    return BTCChinaAdapters.adaptTrades(btcChinaTrades, currencyPair);
  }

}
