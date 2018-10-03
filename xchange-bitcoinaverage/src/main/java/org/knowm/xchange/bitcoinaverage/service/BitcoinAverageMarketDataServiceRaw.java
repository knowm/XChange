package org.knowm.xchange.bitcoinaverage.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinaverage.BitcoinAverage;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTicker;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTickers;
import si.mazi.rescu.RestProxyFactory;

/**
 * Implementation of the raw market data service for BitcoinAverage
 *
 * <ul>
 *   <li>Provides access to various market data values
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
    this.bitcoinAverage =
        RestProxyFactory.createProxy(
            BitcoinAverage.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
  }

  public BitcoinAverageTicker getBitcoinAverageTicker(String tradable, String currency)
      throws IOException {

    // Request data
    BitcoinAverageTicker bitcoinAverageTicker = bitcoinAverage.getTicker(tradable + currency);

    return bitcoinAverageTicker;
  }

  public BitcoinAverageTickers getBitcoinAverageShortTickers(String crypto) throws IOException {

    // Request data
    BitcoinAverageTickers bitcoinAverageTicker = bitcoinAverage.getShortTickers(crypto);

    return bitcoinAverageTicker;
  }
}
