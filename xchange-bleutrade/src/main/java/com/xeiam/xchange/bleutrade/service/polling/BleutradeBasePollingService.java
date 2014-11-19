package com.xeiam.xchange.bleutrade.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bleutrade.Bleutrade;
import com.xeiam.xchange.bleutrade.BleutradeAdapters;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import com.xeiam.xchange.bleutrade.service.BleutradeBaseService;
import com.xeiam.xchange.bleutrade.service.BleutradeDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BleutradeBasePollingService<T extends Bleutrade> extends BleutradeBaseService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T bleutrade;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BleutradeBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bleutrade = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = BleutradeDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  protected int nextNonce() {

    return lastNonce.incrementAndGet();
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    BleutradeMarketsReturn response = bleutrade.getBleutradeMarkets();
    return BleutradeAdapters.adaptBleutradeCurrencyPairs(response);
  }

}
