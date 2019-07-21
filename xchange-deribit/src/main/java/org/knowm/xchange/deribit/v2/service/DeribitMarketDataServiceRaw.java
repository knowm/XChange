package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.marketdata.*;

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
    return deribit.getInstruments(currency).getResult();
  }

  public List<DeribitCurrency> getDeribitCurrencies() throws IOException {
    return deribit.getCurrencies().getResult();
  }

  public DeribitOrderBook getDeribitOrderBook(String instrumentName) throws IOException {
    return deribit.getOrderBook(instrumentName).getResult();
  }

  public DeribitOrderBook getDeribitOrderBook(String instrumentName, int depth) throws IOException {
    return deribit.getOrderBook(instrumentName, depth).getResult();
  }

  public DeribitTrades getDeribitLastTrades(String instrumentName) throws IOException {
    return deribit.getLastTrades(instrumentName).getResult();
  }

  public List<DeribitSummary> getDeribitSummary(String instrumentName) throws IOException {
    return deribit.getSummary(instrumentName).getResult();
  }

  public DeribitTicker getDeribitTicker(String instrumentName) throws IOException {
    return deribit.getTicker(instrumentName).getResult();
  }
}
