package org.knowm.xchange.bankera.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bankera.Bankera;
import org.knowm.xchange.bankera.BankeraAuthenticated;
import org.knowm.xchange.bankera.dto.BankeraException;
import org.knowm.xchange.bankera.dto.BankeraToken;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class BankeraBaseService extends BaseExchangeService implements BaseService {

  protected final Bankera bankera;
  protected final BankeraAuthenticated bankeraAuthenticated;

  public BankeraBaseService(Exchange exchange) {
    super(exchange);
    bankera =
        ExchangeRestProxyBuilder.forInterface(Bankera.class, exchange.getExchangeSpecification())
            .build();
    bankeraAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BankeraAuthenticated.class, exchange.getExchangeSpecification())
            .build();
  }

  public BankeraToken createToken() throws BankeraException {
    String clientId = (String) exchange.getExchangeSpecification().getParameter("clientId");
    String clientSecret = (String) exchange.getExchangeSpecification().getParameter("clientSecret");
    return bankera.getToken("client_credentials", clientId, clientSecret);
  }
}
