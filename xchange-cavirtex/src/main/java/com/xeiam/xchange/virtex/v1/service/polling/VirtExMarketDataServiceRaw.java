package com.xeiam.xchange.virtex.v1.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.virtex.v1.VirtEx;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTrade;

/**
 * <p>
 * Implementation of the market data service for VirtEx
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */

@Deprecated
public class VirtExMarketDataServiceRaw extends VirtexBasePollingService {

  private final VirtEx virtEx;

  /**
   * Constructor
   *
   * @param exchange
   */
  public VirtExMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.virtEx = RestProxyFactory.createProxy(VirtEx.class, exchange.getExchangeSpecification().getSslUri());
  }

  public VirtExTicker getVirtExTicker(String currency) throws IOException {

    // Request data
    VirtExTicker virtExTicker = virtEx.getTicker(currency);

    // Adapt to XChange DTOs
    return virtExTicker;
  }

  public VirtExDepth getVirtExOrderBook(String currency) throws IOException {

    // Request data
    VirtExDepth virtExDepth = virtEx.getFullDepth(currency);

    return virtExDepth;
  }

  public VirtExTrade[] getVirtExTrades(String currency) throws IOException {

    // Request data
    VirtExTrade[] virtExTrades = virtEx.getTrades(currency);

    return virtExTrades;
  }

}
