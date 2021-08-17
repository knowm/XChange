package org.knowm.xchange.cobinhood.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.cobinhood.CobinhoodAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

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
        ExchangeRestProxyBuilder.forInterface(
                CobinhoodAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
  }
}
