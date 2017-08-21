package org.knowm.xchange.hitbtc.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.dto.general.HitbtcSort;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrade.HitbtcTradesSortField;
import org.knowm.xchange.service.marketdata.MarketDataService;

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

    if (args.length == 0) {
      return HitbtcAdapters.adaptTrades(getHitbtcTrades(currencyPair, DEFAULT_MAX_RESULTS, 0, null, null, 0), currencyPair);
    }

    long from = (Long) args[0];
    HitbtcTradesSortField sortBy = (HitbtcTradesSortField) args[1];
    HitbtcSort sortDirection = (HitbtcSort) args[2];
    long startIndex = (Long) args[3];
    Integer maxResults = (Integer) args[4];

    return HitbtcAdapters.adaptTrades(getHitbtcTrades(currencyPair, maxResults, from, sortBy, sortDirection, startIndex), currencyPair);
  }
}
