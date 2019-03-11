package org.knowm.xchange.koinim.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.koinim.KoinimAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author ahmetoz */
public class KoinimMarketDataService extends KoinimMarketDataServiceRaw
    implements MarketDataService {

  public KoinimMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return KoinimAdapters.adaptTicker(getKoinimTicker(), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotAvailableFromExchangeException();
  }
}
