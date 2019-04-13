package org.knowm.xchange.deribit.v2.service;

import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Implementation of the market data service for Bitmex
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class DeribitMarketDataService extends DeribitMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public DeribitMarketDataService(DeribitExchange exchange) {

    super(exchange);
  }
}
