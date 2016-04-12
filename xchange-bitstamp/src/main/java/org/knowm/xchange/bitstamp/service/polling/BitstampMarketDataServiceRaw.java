package org.knowm.xchange.bitstamp.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.Bitstamp;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import org.knowm.xchange.exceptions.ExchangeException;

import si.mazi.rescu.RestProxyFactory;

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
    DAY, HOUR, MINUTE
  }
}
