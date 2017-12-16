package org.knowm.xchange.liqui.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.liqui.LiquiAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class LiquiMarketDataService extends LiquiMarketDataServiceRaw implements MarketDataService {
  public LiquiMarketDataService(final Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(final CurrencyPair currencyPair, final Object... args) throws IOException {
    return LiquiAdapters.adaptTicker(getTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(final CurrencyPair currencyPair, final Object... args) throws IOException {
    return LiquiAdapters.adaptOrderBook(getDepth(currencyPair, 2000), currencyPair);
  }

  @Override
  public Trades getTrades(final CurrencyPair currencyPair, final Object... args) throws IOException {
    return LiquiAdapters.adaptTrades(getTrades(currencyPair, 2000), currencyPair);
  }
}
