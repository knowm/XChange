package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcurex.Bitcurex;
import com.xeiam.xchange.bitcurex.BitcurexAuthenticated;
import com.xeiam.xchange.bitcurex.service.BitcurexDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class BitcurexBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final BitcurexAuthenticated bitcurexAuthenticated;
  protected final BitcurexDigest signatureCreator;

  protected final Bitcurex bitcurex;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcurexBasePollingService(Exchange exchange) {

    super(exchange);
    this.bitcurex = RestProxyFactory.createProxy(Bitcurex.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = BitcurexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), exchange.getExchangeSpecification()
        .getApiKey());
    this.bitcurexAuthenticated = RestProxyFactory.createProxy(BitcurexAuthenticated.class, exchange.getExchangeSpecification().getSslUri());

  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
