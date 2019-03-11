package org.knowm.xchange.cobinhood.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cobinhood.CobinhoodAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class CobinhoodBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final CobinhoodAuthenticated cobinhood;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected CobinhoodBaseService(Exchange exchange) {
    super(exchange);
    this.cobinhood =
        RestProxyFactory.createProxy(
            CobinhoodAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
  }
}
