package com.xeiam.xchange.btccentral.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btccentral.BTCCentral;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

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
}
