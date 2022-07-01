package org.knowm.xchange.enigma.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.enigma.EnigmaAuthenticated;
import org.knowm.xchange.enigma.dto.account.EnigmaCredentials;
import org.knowm.xchange.enigma.dto.account.EnigmaLoginResponse;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.SynchronizedValueFactory;

public abstract class EnigmaBaseService extends BaseExchangeService implements BaseService {

  protected final EnigmaAuthenticated enigmaAuthenticated;
  protected final SynchronizedValueFactory<Long> nonceFactory;

  protected EnigmaBaseService(Exchange exchange) {
    super(exchange);
    this.enigmaAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                EnigmaAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.nonceFactory = exchange.getNonceFactory();
  }

  public void login() throws IOException {
    ExchangeSpecification currentSpec = exchange.getExchangeSpecification();
    String username = currentSpec.getUserName();
    String password = currentSpec.getPassword();
    EnigmaLoginResponse loginResponse =
        enigmaAuthenticated.login(new EnigmaCredentials(username, password));
    currentSpec.setApiKey(loginResponse.getKey());
    exchange.applySpecification(currentSpec);
  }

  protected String accessToken() {
    return exchange.getExchangeSpecification().getApiKey();
  }
}
