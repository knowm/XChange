package org.knowm.xchange.bitstamp.service;

import java.io.IOException;

import javax.annotation.Nullable;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampV2;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import org.knowm.xchange.currency.CurrencyPair;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author gnandiga
 */
public class BitstampMarketDataServiceRaw extends BitstampBaseService {

  private final BitstampV2 bitstampV2;

  public BitstampMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    // allow HTTP connect- and read-timeout to be set per exchange
    ClientConfig rescuConfig = new ClientConfig(); // default rescu config
    int customHttpConnTimeout = exchange.getExchangeSpecification().getHttpConnTimeout();
    if (customHttpConnTimeout > 0) {
      rescuConfig.setHttpConnTimeout(customHttpConnTimeout);
    }
    int customHttpReadTimeout = exchange.getExchangeSpecification().getHttpReadTimeout();
    if (customHttpReadTimeout > 0) {
      rescuConfig.setHttpReadTimeout(customHttpReadTimeout);
    }

    this.bitstampV2 = RestProxyFactory.createProxy(BitstampV2.class, exchange.getExchangeSpecification().getSslUri(), rescuConfig);
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
