package org.knowm.xchange.deribit.v1.service;

import org.knowm.xchange.deribit.v1.DeribitExchange;
import org.knowm.xchange.deribit.v1.dto.marketdata.DeribitCurrency;
import org.knowm.xchange.deribit.v1.dto.marketdata.DeribitInstrument;

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

  public List<DeribitInstrument> getInstruments() throws IOException {
    return deribit.getInstruments().getResult();
  }

  public List<DeribitCurrency> getCurrencies() throws IOException {
    return deribit.getCurrencies().getResult();
  }

}
