package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.deribit.v2.DeribitErrorAdapter;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.marketdata.*;
import org.knowm.xchange.deribit.v2.dto.marketdata.response.*;
import si.mazi.rescu.HttpStatusIOException;

/**
 * Implementation of the market data service for Bitmex
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
@SuppressWarnings({"WeakerAccess", "JavaDoc"})
public class DeribitMarketDataServiceRaw extends DeribitBaseExchange {

  /**
   * Constructor
   *
   * @param exchange
   */
  public DeribitMarketDataServiceRaw(DeribitExchange exchange) {

    super(exchange);
  }

  public List<DeribitInstrument> getDeribitActiveInstruments(String currency) throws IOException {

    DeribitInstrumentsResponse response = null;

    try {
      response = deribit.getInstruments(currency);
    } catch (HttpStatusIOException ex) {
      DeribitErrorAdapter.adapt(ex);
    }

    return response.getResult();
  }

  public List<DeribitCurrency> getDeribitCurrencies() throws IOException {

      DeribitCurrenciesResponse response = null;

      try {
          response = deribit.getCurrencies();
      } catch (HttpStatusIOException ex) {
          DeribitErrorAdapter.adapt(ex);
      }

      return response.getResult();
  }

  public DeribitOrderBook getDeribitOrderBook(String instrumentName) throws IOException {

      DeribitOrderBookResponse response = null;

      try {
          response = deribit.getOrderBook(instrumentName);
      } catch (HttpStatusIOException ex) {
          DeribitErrorAdapter.adapt(ex);
      }

      return response.getResult();
  }

  public DeribitOrderBook getDeribitOrderBook(String instrumentName, int depth) throws IOException {

      DeribitOrderBookResponse response = null;

      try {
          response = deribit.getOrderBook(instrumentName, depth);
      } catch (HttpStatusIOException ex) {
          DeribitErrorAdapter.adapt(ex);
      }

      return response.getResult();
  }

  public DeribitTrades getDeribitLastTrades(String instrumentName) throws IOException {

      DeribitTradesResponse response = null;

      try {
          response = deribit.getLastTrades(instrumentName);
      } catch (HttpStatusIOException ex) {
          DeribitErrorAdapter.adapt(ex);
      }

      return response.getResult();
  }

  public List<DeribitSummary> getDeribitSummary(String instrumentName) throws IOException {

      DeribitSummaryResponse response = null;

      try {
          response = deribit.getSummary(instrumentName);
      } catch (HttpStatusIOException ex) {
          DeribitErrorAdapter.adapt(ex);
      }

      return response.getResult();
  }

  public DeribitTicker getDeribitTicker(String instrumentName) throws IOException {

    DeribitTickerResponse response = null;

    try {
      response = deribit.getTicker(instrumentName);
    } catch (HttpStatusIOException ex) {
      DeribitErrorAdapter.adapt(ex);
    }

    return response.getResult();
  }
}
