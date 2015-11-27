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

import com.xeiam.xchange.utils.RestrictedSSLSocketFactory;
import si.mazi.rescu.ClientConfig;
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

    ClientConfig config = new ClientConfig();
    // cryptonit server disconnects immediately or raises "protocol version" if connected via these protocol versions
    config.setSslSocketFactory(new RestrictedSSLSocketFactory(new String[]{"SSLv2Hello", "TLSv1","TLSv1.1"}, null));

    this.cryptonit = RestProxyFactory.createProxy(Cryptonit.class, exchange.getExchangeSpecification().getSslUri(), config);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    currencyPairs.addAll(CryptonitAdapters.adaptCurrencyPairs(cryptonit.getPairs()));

    return currencyPairs;
  }
}
