package org.knowm.xchange.chbtc.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.chbtc.ChbtcAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class ChbtcMarketDataService extends ChbtcMarketDataServiceRaw implements MarketDataService {

  public ChbtcMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return ChbtcAdapters.adaptTicker(getChbtcTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return ChbtcAdapters.adaptOrderBook(getChbtcOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    Integer sinceTid = args.length > 0 && args[0] != null ? ((Number) args[0]).intValue() : null;
    return ChbtcAdapters.adaptTrades(getChbtcTransactions(currencyPair, sinceTid), currencyPair);
  }
}
