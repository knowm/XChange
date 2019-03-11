package org.knowm.xchange.koineks.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.koineks.KoineksAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author semihunaldi */
public class KoineksMarketDataService extends KoineksMarketDataServiceRaw
    implements MarketDataService {

  public KoineksMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return KoineksAdapters.adaptTicker(getKoineksTicker(), currencyPair);
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
