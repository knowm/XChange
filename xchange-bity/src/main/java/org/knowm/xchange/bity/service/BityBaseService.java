package org.knowm.xchange.bity.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bity.BityAuthenticated;
import org.knowm.xchange.bity.dto.BityException;
import org.knowm.xchange.bity.dto.account.BityToken;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class BityBaseService extends BaseExchangeService implements BaseService {

  protected final BityAuthenticated bity;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BityBaseService(Exchange exchange) {
    super(exchange);

    bity =
        ExchangeRestProxyBuilder.forInterface(
                BityAuthenticated.class, exchange.getExchangeSpecification())
            .build();
  }

  public BityToken createToken() throws BityException {
    String clientId = (String) exchange.getExchangeSpecification().getParameter("clientId");
    return bity.createToken(
        clientId,
        "password",
        exchange.getExchangeSpecification().getApiKey(),
        exchange.getExchangeSpecification().getSecretKey());
  }
}
