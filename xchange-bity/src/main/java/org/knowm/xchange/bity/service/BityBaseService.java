package org.knowm.xchange.bity.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bity.BityAuthenticated;
import org.knowm.xchange.bity.dto.BityException;
import org.knowm.xchange.bity.dto.account.BityToken;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

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
        RestProxyFactory.createProxy(
            BityAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
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
