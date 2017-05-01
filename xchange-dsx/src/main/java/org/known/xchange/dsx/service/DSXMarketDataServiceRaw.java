package org.known.xchange.dsx.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.known.xchange.dsx.dto.marketdata.DSXOrderbookWrapper;
import org.known.xchange.dsx.dto.marketdata.DSXTickerWrapper;
import org.known.xchange.dsx.dto.marketdata.DSXTradesWrapper;

/**
 * @author Mikhail Wall
 */

public class DSXMarketDataServiceRaw extends DSXBaseService {

  protected static final int FULL_SIZE = 2000;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected DSXMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public DSXTickerWrapper getDSXTicker(String pairs) throws IOException {

    return dsx.getTicker(pairs.toLowerCase(), 1);
  }

  public DSXOrderbookWrapper getDSXOrderbook(String pairs) throws IOException {

    return dsx.getOrderbook(pairs.toLowerCase(), 1);
  }

  public DSXTradesWrapper getDSXTrades(String pairs, int size) throws IOException {

    if (size < 1) {
      size = 1;
    }

    if (size > FULL_SIZE) {
      size = FULL_SIZE;
    }

    return dsx.getTrades(pairs.toLowerCase(), size, 1);
  }

}
