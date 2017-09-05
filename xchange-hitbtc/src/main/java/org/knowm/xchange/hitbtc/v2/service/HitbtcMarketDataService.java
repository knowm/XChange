package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSort;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTrade;
import org.knowm.xchange.hitbtc.v2.internal.HitbtcAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HitbtcMarketDataService extends HitbtcMarketDataServiceRaw implements MarketDataService {

  public HitbtcMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return HitbtcAdapters.adaptTicker(getHitbtcTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return HitbtcAdapters.adaptOrderBook(getHitbtcOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    if (args == null || args.length == 0) {
      return HitbtcAdapters.adaptTrades(getHitbtcTrades(currencyPair), currencyPair);
    }

    long from = (Long) args[0]; // <trade_id> or <timestamp>
    HitbtcTrade.HitbtcTradesSortField sortBy = (HitbtcTrade.HitbtcTradesSortField) args[1]; // "trade_id" or "timestamp"
    HitbtcSort sortDirection = (HitbtcSort) args[2]; // "asc" or "desc"
    long startIndex = (Long) args[3]; // 0
    long max_results = (Long) args[4]; // max is 1000

    return HitbtcAdapters.adaptTrades(getHitbtcTrades(currencyPair, from, sortBy, sortDirection, startIndex, max_results), currencyPair);
  }

}
