package com.xeiam.xchange.bitstamp.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitstamp.Bitstamp;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTicker;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import com.xeiam.xchange.exceptions.ExchangeException;

/**
 * @author gnandiga
 */
public class BitstampMarketDataServiceRaw extends BitstampBasePollingService {

  private final Bitstamp bitstamp;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitstampMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitstamp = RestProxyFactory.createProxy(Bitstamp.class, exchange.getExchangeSpecification().getSslUri());
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
    } else if (args.length == 1) {
      BitstampTime bitstampTime = (BitstampTime) args[0];
      transactions = bitstamp.getTransactions(bitstampTime.toString().toLowerCase()); // default values: limit=100
    } else {
      throw new ExchangeException("Invalid argument length. Must be 0, or 1.");
    }
    return transactions;
  }

  public enum BitstampTime {
    HOUR, MINUTE
  }
}
