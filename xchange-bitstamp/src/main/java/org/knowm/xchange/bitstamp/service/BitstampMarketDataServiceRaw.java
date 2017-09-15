package org.knowm.xchange.bitstamp.service;

import java.io.IOException;

import javax.annotation.Nullable;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampV2;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import org.knowm.xchange.currency.CurrencyPair;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author gnandiga
 */
public class BitstampMarketDataServiceRaw extends BitstampBaseService {

  private final BitstampV2 bitstampV2;

  public BitstampMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitstampV2 = RestProxyFactory.createProxy(BitstampV2.class, exchange.getExchangeSpecification().getSslUri());
  }

  public BitstampTicker getBitstampTicker(CurrencyPair pair) throws IOException {
    return bitstampV2.getTicker(new BitstampV2.Pair(pair));
  }

  public BitstampOrderBook getBitstampOrderBook(CurrencyPair pair) throws IOException {
    return bitstampV2.getOrderBook(new BitstampV2.Pair(pair));
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
