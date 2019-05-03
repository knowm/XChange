package org.knowm.xchange.enigma.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.EnigmaAuthenticated;
import org.knowm.xchange.enigma.exception.*;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public abstract class EnigmaBaseService extends BaseExchangeService implements BaseService {

  protected final EnigmaAuthenticated enigmaAuthenticated;
  protected final SynchronizedValueFactory<Long> nonceFactory;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected EnigmaBaseService(Exchange exchange) {
    super(exchange);
    this.enigmaAuthenticated =
        RestProxyFactory.createProxy(
            EnigmaAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.nonceFactory = exchange.getNonceFactory();
  }

  protected String accessToken() {
    return exchange.getExchangeSpecification().getApiKey();
  }

  protected RuntimeException handleError(HttpStatusIOException httpStatusIOException) {
    switch (httpStatusIOException.getHttpStatusCode()) {
      case 0:
        return new EnigmaAccessDeniedException();
      case 5:
        return new EnigmaOrderIssueException();
      case 6:
        return new EnigmaNumberFormatException();
      case 55:
        return new EnigmaRequestForQuoteException();
      case 56:
        return new EnigmaRiskLimitException();
      case 61:
        return new EnigmaBooleanFormatException();
      case 100:
        return new EnigmaUsernameException();
      case 105:
        return new EnigmaPasswordException();
      case 500:
        return new EnigmaVagueException();
    }
    return new RuntimeException();
  }
}
