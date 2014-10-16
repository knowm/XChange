package com.xeiam.xchange.cryptonit.v2.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptonit.v2.Cryptonit;
import com.xeiam.xchange.cryptonit.v2.CryptonitAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class CryptonitBasePollingService<T extends Cryptonit> extends BaseExchangeService implements BasePollingService {

  protected final T cryptonit;
  private final Set<CurrencyPair> currencyPairs;
  protected final String apiKey;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CryptonitBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.cryptonit = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    this.currencyPairs = new HashSet<CurrencyPair>();
    this.apiKey = exchangeSpecification.getApiKey();
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty())
      currencyPairs.addAll(CryptonitAdapters.adaptCurrencyPairs(cryptonit.getPairs()));

    return currencyPairs;
  }
}
