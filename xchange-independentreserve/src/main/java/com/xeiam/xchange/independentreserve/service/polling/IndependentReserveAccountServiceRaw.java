package com.xeiam.xchange.independentreserve.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.independentreserve.IndependentReserveAuthenticated;
import com.xeiam.xchange.independentreserve.dto.account.IndependentReserveBalance;
import com.xeiam.xchange.independentreserve.dto.auth.AuthAggregate;
import com.xeiam.xchange.independentreserve.service.IndependentReserveDigest;
import com.xeiam.xchange.independentreserve.util.ExchangeEndpoint;

import si.mazi.rescu.RestProxyFactory;

/**
 * Author: Kamil Zbikowski Date: 4/10/15
 */
public class IndependentReserveAccountServiceRaw extends IndependentReserveBasePollingService {

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
