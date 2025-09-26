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
 * @author Piotr Ładyżyński Updated for Bitso API v3
 */
public class BitsoMarketDataService extends BitsoMarketDataServiceRaw implements MarketDataService {

  public BitsoMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(Instrument currencyPair, Object... args) throws IOException {
    return BitsoAdapters.adaptTicker(getBitsoTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(Instrument currencyPair, Object... args) throws IOException {
    return BitsoAdapters.adaptOrderBook(getBitsoOrderBook(currencyPair), currencyPair, 1000);
  }

  @Override
  public Trades getTrades(Instrument currencyPair, Object... args) throws IOException {
    return BitsoAdapters.adaptTrades(getBitsoTrades(currencyPair, args), currencyPair);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTrades((Instrument) currencyPair, args);
  }
}
