package com.xeiam.xchange.bitstamp.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.Bitstamp;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTicker;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTransaction;

/**
 * @author gnandiga
 */
public class BitstampMarketDataServiceRaw extends BitstampBasePollingService {

  private final Bitstamp bitstamp;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitstampMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitstamp = RestProxyFactory.createProxy(Bitstamp.class, exchangeSpecification.getSslUri());
  }

  public BitstampTicker getBitstampTicker() throws IOException {

    return bitstamp.getTicker();
  }

  public BitstampOrderBook getBitstampOrderBook() throws IOException {

    return bitstamp.getOrderBook();
  }

  public BitstampTransaction[] getBitstampTransactions(Object... args) throws IOException {

    BitstampTransaction[] transactions = null;

    if (args.length == 0) {
      transactions = bitstamp.getTransactions(); // default values: offset=0, limit=100
    }
    else if (args.length == 1) {
      BitstampTime bitstampTime = (BitstampTime) args[0];
      transactions = bitstamp.getTransactions(bitstampTime.toString().toLowerCase()); // default values: limit=100
    }
    else {
      throw new ExchangeException("Invalid argument length. Must be 0, or 1.");
    }
    return transactions;
  }

  public enum BitstampTime {
    HOUR, MINUTE
  }
}
