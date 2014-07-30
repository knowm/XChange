package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.Bitfinex;
import com.xeiam.xchange.bitfinex.v1.BitfinexAdapters;
import com.xeiam.xchange.bitfinex.v1.service.BitfinexBaseService;
import com.xeiam.xchange.bitfinex.v1.service.BitfinexHmacPostBodyDigest;
import com.xeiam.xchange.bitfinex.v1.service.BitfinexPayloadDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BitfinexBasePollingService<T extends Bitfinex> extends BitfinexBaseService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T bitfinex;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;
  private final Set<CurrencyPair> currencyPairs;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitfinexBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitfinex = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = BitfinexHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
    this.payloadCreator = new BitfinexPayloadDigest();
    this.currencyPairs = new HashSet<CurrencyPair>();
  }

  protected int nextNonce() {

    return lastNonce.incrementAndGet();
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty()) {
      for (String symbol : bitfinex.getSymbols()) {
        currencyPairs.add(BitfinexAdapters.adaptCurrencyPair(symbol));
      }
    }

    return currencyPairs;
  }
}
