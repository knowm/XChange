package org.knowm.xchange.bitstamp.service;

import java.io.IOException;

import javax.annotation.Nullable;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.Bitstamp;
import org.knowm.xchange.bitstamp.BitstampV2;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author gnandiga
 */
public class BitstampMarketDataServiceRaw extends BitstampBaseService {

  @Deprecated
  private final Bitstamp bitstamp;

  private final BitstampV2 bitstampV2;

  public BitstampMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitstamp = RestProxyFactory.createProxy(Bitstamp.class, exchange.getExchangeSpecification().getSslUri());
    this.bitstampV2 = RestProxyFactory.createProxy(BitstampV2.class, exchange.getExchangeSpecification().getSslUri());
  }

  /** @deprecated Use {@link #getBitstampTicker(CurrencyPair)}. */
  @Deprecated
  public BitstampTicker getBitstampTicker() throws IOException {
    return getBitstampTicker(CurrencyPair.BTC_USD);
  }

  public BitstampTicker getBitstampTicker(CurrencyPair pair) throws IOException {
    return bitstampV2.getTicker(new BitstampV2.Pair(pair));
  }

  /** @deprecated Use {@link #getBitstampOrderBook(CurrencyPair)}. */
  @Deprecated
  public BitstampOrderBook getBitstampOrderBook() throws IOException {
    return getBitstampOrderBook(CurrencyPair.BTC_USD);
  }

  public BitstampOrderBook getBitstampOrderBook(CurrencyPair pair) throws IOException {
    return bitstampV2.getOrderBook(new BitstampV2.Pair(pair));
  }

  /**
   * @deprecated Use {{@link #getTransactions(CurrencyPair, BitstampTime)}} instead.
   */
  @Deprecated
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

  public BitstampTransaction[] getTransactions(CurrencyPair pair, @Nullable BitstampTime time) throws IOException {

    return bitstampV2.getTransactions(new BitstampV2.Pair(pair), time);
  }

  public enum BitstampTime {
    DAY, HOUR, MINUTE;

    @Override
    public String toString() {
      return super.toString().toLowerCase();
    }
  }
}
