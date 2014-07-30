package com.xeiam.xchange.bitcoinaverage.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinaverage.BitcoinAverage;
import com.xeiam.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTicker;
import com.xeiam.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTickers;

/**
 * <p>
 * Implementation of the raw market data service for BitcoinAverage
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcoinAverageMarketDataServiceRaw extends BitcoinAverageBasePollingService {

  private final BitcoinAverage bitcoinAverage;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitcoinAverageMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitcoinAverage = RestProxyFactory.createProxy(BitcoinAverage.class, exchangeSpecification.getSslUri());
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
