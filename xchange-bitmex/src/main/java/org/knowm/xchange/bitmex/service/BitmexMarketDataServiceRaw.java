package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;

/**
 * <p>
 * Implementation of the market data service for Bitmex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitmexMarketDataServiceRaw extends BitmexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<BitmexTicker> getAllTickers() throws IOException {
    try {
      return bitmex.getTickers();
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexTicker> getTicker(String symbol) throws IOException {
    try {
      return bitmex.getTicker(symbol);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexTicker> getActiveTickers() throws IOException {
    try {
      return bitmex.getActiveTickers();
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }
}
