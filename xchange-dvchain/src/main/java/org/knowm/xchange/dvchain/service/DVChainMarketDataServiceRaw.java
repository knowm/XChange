package org.knowm.xchange.dvchain.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dvchain.dto.DVChainException;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainMarketResponse;

/**
 * Implementation of the market data service for DVChain
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class DVChainMarketDataServiceRaw extends DVChainBaseService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public DVChainMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public DVChainMarketResponse getMarketData() throws IOException {
    try {
      return dvChain.getPrices(authToken);
    } catch (DVChainException e) {
      throw handleException(e);
    }
  }
}
