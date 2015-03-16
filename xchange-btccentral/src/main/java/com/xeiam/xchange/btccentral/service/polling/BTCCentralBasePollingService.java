package com.xeiam.xchange.btccentral.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btccentral.BTCCentral;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author kpysniak
 */
public class BTCCentralBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final BTCCentral btcCentral;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BTCCentralBasePollingService(Exchange exchange) {

    super(exchange);
    this.btcCentral = RestProxyFactory.createProxy(BTCCentral.class, exchange.getExchangeSpecification().getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
