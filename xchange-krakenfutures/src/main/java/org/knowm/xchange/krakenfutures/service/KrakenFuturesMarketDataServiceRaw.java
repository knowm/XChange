package org.knowm.xchange.krakenfutures.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.krakenfutures.KrakenFuturesAdapters;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesInstruments;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesOrderBook;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesPublicFills;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesTicker;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesTickers;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;

/** @author Jean-Christophe Laruelle */
public class KrakenFuturesMarketDataServiceRaw extends KrakenFuturesBaseService {

  /**
   * Constructor
   *
   * @param exchange of KrakenFutures
   */
  public KrakenFuturesMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public KrakenFuturesTicker getKrakenFuturesTicker(Instrument instrument)
      throws IOException {

    return getKrakenFuturesTickers().getTicker(KrakenFuturesAdapters.adaptKrakenFuturesSymbol(instrument));
  }

  public KrakenFuturesTickers getKrakenFuturesTickers() throws IOException {

    KrakenFuturesTickers tickers = krakenFuturesAuthenticated.getTickers();

    if (tickers.isSuccess()) {
      return tickers;
    } else {
      throw new ExchangeException("Error getting CF tickers: " + tickers.getError());
    }
  }

  public KrakenFuturesInstruments getKrakenFuturesInstruments() throws IOException {

    KrakenFuturesInstruments instruments = krakenFuturesAuthenticated.getInstruments();

    if (instruments.isSuccess()) {
      return instruments;
    } else {
      throw new ExchangeException("Error getting CF instruments: " + instruments.getError());
    }
  }

  public KrakenFuturesOrderBook getKrakenFuturesOrderBook(Instrument instrument)
      throws IOException {

    KrakenFuturesOrderBook orderBook =
        krakenFuturesAuthenticated.getOrderBook(KrakenFuturesAdapters.adaptKrakenFuturesSymbol(instrument));

    if (orderBook.isSuccess()) {
      orderBook.setInstrument(instrument);
      return orderBook;
    } else {
      throw new ExchangeException("Error getting CF order book: " + orderBook.getError());
    }
  }

  public KrakenFuturesPublicFills getKrakenFuturesTrades(Instrument instrument)
      throws IOException {

    KrakenFuturesPublicFills publicFills =
        krakenFuturesAuthenticated.getHistory(KrakenFuturesAdapters.adaptKrakenFuturesSymbol(instrument));

    if (publicFills.isSuccess()) {
      publicFills.setInstrument(instrument);
      return publicFills;
    } else {
      throw new ExchangeException("Error getting CF public fills: " + publicFills.getError());
    }
  }
}
