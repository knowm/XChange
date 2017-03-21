package org.knowm.xchange.independentreserve.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.independentreserve.IndependentReserveAuthenticated;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBalance;
import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;
import org.knowm.xchange.independentreserve.util.ExchangeEndpoint;

import si.mazi.rescu.RestProxyFactory;

/**
 * Author: Kamil Zbikowski Date: 4/10/15
 */
public class IndependentReserveAccountServiceRaw extends IndependentReserveBaseService {

  private final IndependentReserveDigest signatureCreator;
  private final IndependentReserveAuthenticated independentReserveAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected IndependentReserveAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.independentReserveAuthenticated = RestProxyFactory.createProxy(IndependentReserveAuthenticated.class,
        exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = IndependentReserveDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getApiKey(), exchange.getExchangeSpecification().getSslUri());
  }

  public IndependentReserveBalance getIndependentReserveBalance() throws IOException {
    Long nonce = exchange.getNonceFactory().createValue();
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    AuthAggregate authAggregate = new AuthAggregate(apiKey, nonce);

    authAggregate.setSignature(signatureCreator.digestParamsToString(ExchangeEndpoint.GET_ACCOUNTS, nonce, authAggregate.getParameters()));
    IndependentReserveBalance independentReserveBalance = independentReserveAuthenticated.getBalance(authAggregate);
    if (independentReserveBalance == null) {
      throw new ExchangeException("Error getting balance");
    }
    return independentReserveBalance;
  }

}
