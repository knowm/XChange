package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bittrex.v1.Bittrex;
import com.xeiam.xchange.bittrex.v1.BittrexAdapters;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexSymbol;
import com.xeiam.xchange.bittrex.v1.service.BittrexBaseService;
import com.xeiam.xchange.bittrex.v1.service.BittrexDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BittrexBasePollingService<T extends Bittrex> extends BittrexBaseService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T bittrex;
  protected final ParamsDigest signatureCreator;
  private final Set<CurrencyPair> currencyPairs;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BittrexBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bittrex = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = BittrexDigest.createInstance(exchangeSpecification.getSecretKey());
    this.currencyPairs = new HashSet<CurrencyPair>();
  }

  protected int nextNonce() {

    return lastNonce.incrementAndGet();
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty()) {
      for (BittrexSymbol symbol : bittrex.getSymbols().getSymbols()) {
        currencyPairs.add(BittrexAdapters.adaptCurrencyPair(symbol));
      }
    }

    return currencyPairs;
  }
}
