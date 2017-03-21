package org.knowm.xchange.bitcoinaverage.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinaverage.BitcoinAverage;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTicker;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTickers;

import si.mazi.rescu.RestProxyFactory;

/**
 * <p>
 * Implementation of the raw market data service for BitcoinAverage
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcoinAverageMarketDataServiceRaw extends BitcoinAverageBaseService {

  private final BitcoinAverage bitcoinAverage;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoinAverageMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitcoinAverage = RestProxyFactory.createProxy(BitcoinAverage.class, exchange.getExchangeSpecification().getSslUri());
  }

  public BitcoinAverageTicker getBitcoinAverageTicker(String tradableIdentifier, String currency) throws IOException {

    // Request data
    BitcoinAverageTicker bitcoinAverageTicker = bitcoinAverage.getTicker(currency);

    return bitcoinAverageTicker;
  }

  public BitcoinAverageTickers getBitcoinAverageAllTickers() throws IOException {

    // Request data
    BitcoinAverageTickers bitcoinAverageTicker = bitcoinAverage.getAllTickers();

    return bitcoinAverageTicker;
  }

}
