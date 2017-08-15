package org.knowm.xchange.hitbtc.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrade.HitbtcTradesSortDirection;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrade.HitbtcTradesSortField;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author kpysniak
 */
public class HitbtcMarketDataService extends HitbtcMarketDataServiceRaw implements MarketDataService {

  private static final Integer DEFAULT_MAX_RESULTS = 10;

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


    if (args.length == 0)
      return HitbtcAdapters.adaptTrades(getHitbtcTrades(currencyPair, DEFAULT_MAX_RESULTS,0, null, null, 0), currencyPair);

    //TODO fix this mess
    long from = (Long) args[0]; // <trade_id> or <timestamp>
    HitbtcTradesSortField sortBy = (HitbtcTradesSortField) args[1]; // "trade_id" or "timestamp"
    HitbtcTradesSortDirection sortDirection = (HitbtcTradesSortDirection) args[2]; // "asc" or "desc"
    long startIndex = (Long) args[3]; // 0
    Integer maxResults = (Integer) args[4]; // max is 1000

    return HitbtcAdapters.adaptTrades(getHitbtcTrades(currencyPair, maxResults, from, sortBy, sortDirection, startIndex), currencyPair);
  }
}
