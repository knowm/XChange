package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author ObsessiveOrange
 *         <p>
 *         Implementation of the market data service for BTCChina
 *         </p>
 *         <ul>
 *         <li>Provides access to various market data values</li>
 *         </ul>
 */
public class BTCChinaMarketDataService extends BTCChinaMarketDataServiceRaw implements PollingMarketDataService {

  private final Logger log = LoggerFactory.getLogger(BTCChinaMarketDataService.class);

  /**
   * Constructor
   *
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCChinaMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
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
    BTCChinaDepth btcChinaDepth = getBTCChinaOrderBook(BTCChinaAdapters.adaptMarket(currencyPair));

    // Adapt to XChange DTOs
    List<LimitOrder> asks = BTCChinaAdapters.adaptOrders(btcChinaDepth.getAsks(), currencyPair, OrderType.ASK);
    List<LimitOrder> bids = BTCChinaAdapters.adaptOrders(btcChinaDepth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
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

    final List<BTCChinaTrade> btcChinaTrades;

    if (since != null && limit != null && sinceType != null) {
      btcChinaTrades = getBTCChinaTrades(market, since.longValue(), limit.intValue(), sinceType);
    }
    else if (since != null && limit != null) {
      btcChinaTrades = getBTCChinaTrades(market, since.longValue(), limit.intValue());
    }
    else if (since != null) {
      btcChinaTrades = getBTCChinaTrades(market, since.longValue());
    }
    else if (limit != null) {
      btcChinaTrades = getBTCChinaTrades(market, limit.intValue());
    }
    else {
      btcChinaTrades = getBTCChinaTrades(market);
    }

    // Adapt to XChange DTOs
    return BTCChinaAdapters.adaptTrades(btcChinaTrades, currencyPair);
  }

}
