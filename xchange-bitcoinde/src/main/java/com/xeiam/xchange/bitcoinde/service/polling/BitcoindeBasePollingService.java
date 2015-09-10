package com.xeiam.xchange.bitcoinde.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcoinde.Bitcoinde;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

public class BitcoindeBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final Bitcoinde bitcoinde;

  /**
   * Constructor
   */
  protected BitcoindeBasePollingService(Exchange exchange) {

    super(exchange);
    this.bitcoinde = RestProxyFactory.createProxy(Bitcoinde.class, exchange.getExchangeSpecification().getSslUri());
  }
}
