package org.knowm.xchange.bitso.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.BitsoAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoMarketDataService extends BitsoMarketDataServiceRaw implements MarketDataService {

  public BitsoMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitsoAdapters.adaptTicker(getBitsoTicker(currencyPair), currencyPair);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    return getTicker((CurrencyPair) instrument, args);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitsoAdapters.adaptOrderBook(getBitsoOrderBook(currencyPair), currencyPair, 1000);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    return getOrderBook((CurrencyPair) instrument, args);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitsoAdapters.adaptTrades(getBitsoTransactions(args), currencyPair);
  }
}
