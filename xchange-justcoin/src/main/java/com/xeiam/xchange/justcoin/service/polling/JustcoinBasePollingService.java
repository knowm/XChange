package com.xeiam.xchange.justcoin.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.justcoin.Justcoin;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.AuthUtils;

public class JustcoinBasePollingService<T extends Justcoin> extends BaseExchangeService implements BasePollingService {

  protected final T justcoin;
  private final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public JustcoinBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.justcoin = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty()) {
      for (final JustcoinTicker ticker : justcoin.getTickers()) {
        final CurrencyPair currencyPair = JustcoinAdapters.adaptCurrencyPair(ticker.getId());
        currencyPairs.add(currencyPair);
      }
    }

    return currencyPairs;
  }

  protected String getBasicAuthentication() {

    return AuthUtils.getBasicAuth(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
  }
}
