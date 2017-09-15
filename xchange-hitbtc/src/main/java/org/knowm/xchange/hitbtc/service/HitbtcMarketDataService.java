package org.knowm.xchange.hitbtc.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrades.HitbtcTradesSortDirection;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrades.HitbtcTradesSortField;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Deprecated -- Please migrate to org.knowm.xchange.hitbtc.v2.service.HitbtcMarketDataService
 */
@Deprecated
public class HitbtcMarketDataService extends HitbtcMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HitbtcMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return HitbtcAdapters.adaptTicker(getHitbtcTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return HitbtcAdapters.adaptOrderBook(getHitbtcOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    if (args == null || args.length == 0) {
      return HitbtcAdapters.adaptTrades(getHitbtcTradesRecent(currencyPair, 1000), currencyPair);
    }
    long from = (Long) args[0]; // <trade_id> or <timestamp>
    HitbtcTradesSortField sortBy = (HitbtcTradesSortField) args[1]; // "trade_id" or "timestamp"
    HitbtcTradesSortDirection sortDirection = (HitbtcTradesSortDirection) args[2]; // "asc" or "desc"
    long startIndex = (Long) args[3]; // 0
    long max_results = (Long) args[4]; // max is 1000

    return HitbtcAdapters.adaptTrades(getHitbtcTrades(currencyPair, from, sortBy, sortDirection, startIndex, max_results), currencyPair);
  }
}
