package org.knowm.xchange.gemini.v1.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gemini.v1.GeminiAdapters;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiDepth;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLend;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLendDepth;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiTicker;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiTrade;

/**
 * <p>
 * Implementation of the market data service for Gemini
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class GeminiMarketDataServiceRaw extends GeminiBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GeminiMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public GeminiTicker getGeminiTicker(String pair) throws IOException {

    try {
      GeminiTicker GeminiTicker = Gemini.getTicker(pair);
      return GeminiTicker;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiDepth getGeminiOrderBook(String pair, Integer limitBids, Integer limitAsks) throws IOException {

    try {
      GeminiDepth GeminiDepth;
      if (limitBids == null && limitAsks == null) {
        GeminiDepth = Gemini.getBook(pair);
      } else {
        GeminiDepth = Gemini.getBook(pair, limitBids, limitAsks);
      }
      return GeminiDepth;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiLendDepth getGeminiLendBook(String currency, int limitBids, int limitAsks) throws IOException {

    try {
      GeminiLendDepth GeminiLendDepth = Gemini.getLendBook(currency, limitBids, limitAsks);
      return GeminiLendDepth;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiTrade[] getGeminiTrades(String pair, long sinceTimestamp, int limitTrades) throws IOException {

    try {
      GeminiTrade[] GeminiTrades = Gemini.getTrades(pair, sinceTimestamp, limitTrades);
      return GeminiTrades;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiLend[] getGeminiLends(String currency, long sinceTimestamp, int limitTrades) throws IOException {

    try {
      GeminiLend[] GeminiLends = Gemini.getLends(currency, sinceTimestamp, limitTrades);
      return GeminiLends;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public Collection<String> getGeminiSymbols() throws IOException {

    try {
      return Gemini.getSymbols();
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    try {
      List<CurrencyPair> currencyPairs = new ArrayList<>();
      for (String symbol : Gemini.getSymbols()) {
        currencyPairs.add(GeminiAdapters.adaptCurrencyPair(symbol));
      }
      return currencyPairs;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }
}
