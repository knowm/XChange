package com.xeiam.xchange.cryptonit.v2.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptonit.v2.Cryptonit;
import com.xeiam.xchange.cryptonit.v2.CryptonitAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class CryptonitBasePollingService<T extends Cryptonit> extends BaseExchangeService implements BasePollingService {

  protected final Cryptonit cryptonit;
  private final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();;

  /**
   * Constructor
   *
   * @param type
   * @param exchange
   */
  //TODO look at this
  public CryptonitBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);
    this.cryptonit = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    currencyPairs.addAll(CryptonitAdapters.adaptCurrencyPairs(cryptonit.getPairs()));

    return currencyPairs;
  }
}
