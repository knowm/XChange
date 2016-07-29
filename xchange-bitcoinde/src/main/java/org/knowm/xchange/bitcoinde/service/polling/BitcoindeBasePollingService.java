package org.knowm.xchange.bitcoinde.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.Bitcoinde;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

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
