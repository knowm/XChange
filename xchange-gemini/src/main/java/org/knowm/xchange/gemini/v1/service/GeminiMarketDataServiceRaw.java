package org.knowm.xchange.gemini.v1.service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gemini.v1.GeminiAdapters;
import org.knowm.xchange.gemini.v1.GeminiUtils;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiDepth;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLend;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLendDepth;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiTicker;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiTrade;
import org.knowm.xchange.gemini.v2.dto.marketdata.GeminiCandle;
import org.knowm.xchange.gemini.v2.dto.marketdata.GeminiTicker2;

/**
 * Implementation of the market data service for Gemini
 *
 * <ul>
 *   <li>Provides access to various market data values
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
      GeminiTicker GeminiTicker = gemini.getTicker(pair);
      return GeminiTicker;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiDepth getGeminiOrderBook(String pair, Integer limitBids, Integer limitAsks)
      throws IOException {

    try {
      GeminiDepth GeminiDepth;
      if (limitBids == null && limitAsks == null) {
        GeminiDepth = gemini.getBook(pair);
      } else {
        GeminiDepth = gemini.getBook(pair, limitBids, limitAsks);
      }
      return GeminiDepth;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiLendDepth getGeminiLendBook(String currency, int limitBids, int limitAsks)
      throws IOException {

    try {
      GeminiLendDepth GeminiLendDepth = gemini.getLendBook(currency, limitBids, limitAsks);
      return GeminiLendDepth;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiTrade[] getGeminiTrades(String pair, long sinceTimestamp, int limitTrades)
      throws IOException {

    try {
      GeminiTrade[] GeminiTrades = gemini.getTrades(pair, sinceTimestamp, limitTrades);
      return GeminiTrades;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiLend[] getGeminiLends(String currency, long sinceTimestamp, int limitTrades)
      throws IOException {

    try {
      GeminiLend[] GeminiLends = gemini.getLends(currency, sinceTimestamp, limitTrades);
      return GeminiLends;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public Collection<String> getGeminiSymbols() throws IOException {

    try {
      return gemini.getSymbols();
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    try {
      List<CurrencyPair> currencyPairs = new ArrayList<>();
      for (String symbol : gemini.getSymbols()) {
        currencyPairs.add(GeminiAdapters.adaptCurrencyPair(symbol));
      }
      return currencyPairs;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiCandle[] getCandles(CurrencyPair pair, Duration interval) throws IOException {
    try {
      String timeFrame;
      if (interval.toDays() > 0) timeFrame = interval.toDays() + "day";
      else if (interval.toHours() > 0) timeFrame = interval.toHours() + "hr";
      else timeFrame = interval.toMinutes() + "m";

      return gemini2.getCandles(GeminiUtils.toPairString(pair), timeFrame);
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiTicker2 getTicker2(CurrencyPair pair) throws IOException {
    try {
      return gemini2.getTicker(GeminiUtils.toPairString(pair));
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }
}
