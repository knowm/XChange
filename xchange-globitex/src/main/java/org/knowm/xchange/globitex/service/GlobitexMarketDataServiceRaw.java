package org.knowm.xchange.globitex.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.globitex.GlobitexAdapters;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexOrderBook;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexSymbols;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexTicker;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexTickers;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexTrades;
import si.mazi.rescu.HttpStatusIOException;

public class GlobitexMarketDataServiceRaw extends GlobitexBaseService {

  protected GlobitexMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public GlobitexSymbols getGlobitexSymbols() throws IOException {
    try {
      return globitex.getSymbols();
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }

  public GlobitexTicker getGlobitexTickerBySymbol(CurrencyPair currencyPair) throws IOException {
    try {
      return globitex.getTickerBySymbol(
          GlobitexAdapters.adaptCurrencyPairToGlobitexSymbol(currencyPair));
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }

  public GlobitexTickers getGlobitexTickers() throws IOException {
    try {
      return globitex.getTickers();
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }

  public GlobitexOrderBook getGlobitexOrderBookBySymbol(CurrencyPair currencyPair)
      throws IOException {
    try {
      return globitex.getOrderBookBySymbol(
          GlobitexAdapters.adaptCurrencyPairToGlobitexSymbol(currencyPair));
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }

  public GlobitexTrades getGlobitexTradesBySymbol(CurrencyPair currencyPair) throws IOException {
    try {
      return globitex.getRecentTradesBySymbol(
          GlobitexAdapters.adaptCurrencyPairToGlobitexSymbol(currencyPair), 100, "object", true);

    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }
}
