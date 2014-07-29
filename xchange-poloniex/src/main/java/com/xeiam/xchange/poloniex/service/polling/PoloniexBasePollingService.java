package com.xeiam.xchange.poloniex.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.poloniex.Poloniex;
import com.xeiam.xchange.poloniex.PoloniexUtils;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import com.xeiam.xchange.poloniex.service.PoloniexBaseService;
import com.xeiam.xchange.poloniex.service.PoloniexDigest;
import com.xeiam.xchange.service.polling.BasePollingService;

public class PoloniexBasePollingService<T extends Poloniex> extends PoloniexBaseService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T poloniex;
  protected final ParamsDigest signatureCreator;
  private final Set<CurrencyPair> currencyPairs;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public PoloniexBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.poloniex = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = PoloniexDigest.createInstance(exchangeSpecification.getSecretKey());
    this.currencyPairs = new HashSet<CurrencyPair>();
  }

  protected int nextNonce() {

    return lastNonce.incrementAndGet();
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    String command = "returnTicker";
    HashMap<String, PoloniexMarketData> marketData = poloniex.getTicker(command);
    Set<String> pairStrings = marketData.keySet();

    currencyPairs.clear();

    for (String pairString : pairStrings) {
      currencyPairs.add(PoloniexUtils.toCurrencyPair(pairString));
    }

    return currencyPairs;
  }
}
