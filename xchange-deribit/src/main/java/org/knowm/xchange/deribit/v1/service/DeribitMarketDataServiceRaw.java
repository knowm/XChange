package org.knowm.xchange.deribit.v1.service;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.deribit.v1.DeribitExchange;
import org.knowm.xchange.deribit.v1.dto.marketdata.*;
import org.knowm.xchange.exceptions.ExchangeException;

import java.io.IOException;
import java.util.List;

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

  public List<DeribitInstrument> getDeribitInstruments() throws IOException {
    return deribit.getInstruments().getResult();
  }

  public List<DeribitCurrency> getDeribitCurrencies() throws IOException {
    return deribit.getCurrencies().getResult();
  }

  public DeribitOrderbook getDeribitOrderbook(String instrument) throws IOException {
    return deribit.getOrderbook(instrument).getResult();
  }

  public DeribitOrderbook getDeribitOrderbook(String instrument, int depth) throws IOException {
    return deribit.getOrderbook(instrument, depth).getResult();
  }

  public List<DeribitTrade> getDeribitLastTrades(String instrument) throws IOException {
    return deribit.getLastTrades(instrument).getResult();
  }

  public DeribitSummary getSummary(String instrument) throws IOException {
    if(StringUtils.containsAny(instrument, "all", "futures", "options")) {
      throw new ExchangeException("Pass specific instrument or use other methods specified for fetching multiple summaries");
    }

    return deribit.getSummary(instrument).getResult();
  }

  public List<DeribitSummary> getAllSummaries() throws IOException {
    return deribit.getSummaries("all").getResult();
  }

  public List<DeribitSummary> getAllSummaries(String currency) throws IOException {
    return deribit.getSummaries("all", currency).getResult();
  }

  public List<DeribitSummary> getFuturesSummaries() throws IOException {
    return deribit.getSummaries("futures").getResult();
  }

  public List<DeribitSummary> getFuturesSummaries(String currency) throws IOException {
    return deribit.getSummaries("futures", currency).getResult();
  }

  public List<DeribitSummary> getOptionsSummaries() throws IOException {
    return deribit.getSummaries("options").getResult();
  }

  public List<DeribitSummary> getOptionsSummaries(String currency) throws IOException {
    return deribit.getSummaries("options", currency).getResult();
  }

}
