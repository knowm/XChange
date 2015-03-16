package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.vircurex.VircurexAuthenticated;

/**
 * @author timmolter
 */
public class VircurexBasePollingService extends BaseExchangeService implements BasePollingService {

  protected VircurexAuthenticated vircurexAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  public VircurexBasePollingService(Exchange exchange) {

    super(exchange);
    this.vircurexAuthenticated = RestProxyFactory.createProxy(VircurexAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
