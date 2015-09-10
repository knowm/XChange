package com.xeiam.xchange.cryptonit.v2.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptonit.v2.Cryptonit;
import com.xeiam.xchange.cryptonit.v2.CryptonitAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

public class CryptonitBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final Cryptonit cryptonit;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptonitBasePollingService(Exchange exchange) {

    super(exchange);

    this.cryptonit = RestProxyFactory.createProxy(Cryptonit.class, exchange.getExchangeSpecification().getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    currencyPairs.addAll(CryptonitAdapters.adaptCurrencyPairs(cryptonit.getPairs()));

    return currencyPairs;
  }
}
