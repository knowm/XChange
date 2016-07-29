package org.knowm.xchange.loyalbit.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.loyalbit.LoyalbitAuthenticated;
import org.knowm.xchange.loyalbit.dto.account.LoyalbitBalance;
import org.knowm.xchange.loyalbit.service.LoyalbitDigest;

import si.mazi.rescu.RestProxyFactory;

public class LoyalbitAccountServiceRaw extends LoyalbitBasePollingService {

  private final LoyalbitDigest signatureCreator;
  private final LoyalbitAuthenticated loyalbitAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected LoyalbitAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.loyalbitAuthenticated = RestProxyFactory.createProxy(LoyalbitAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = LoyalbitDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public LoyalbitBalance getLoyalbitBalance() throws IOException {
    return loyalbitAuthenticated.getBalance(exchange.getExchangeSpecification().getApiKey(), exchange.getNonceFactory(), signatureCreator);
  }
}
