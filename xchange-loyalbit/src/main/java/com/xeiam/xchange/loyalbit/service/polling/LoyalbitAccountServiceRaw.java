package com.xeiam.xchange.loyalbit.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.loyalbit.LoyalbitAuthenticated;
import com.xeiam.xchange.loyalbit.dto.account.LoyalbitBalance;
import com.xeiam.xchange.loyalbit.service.LoyalbitDigest;

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
    this.signatureCreator = LoyalbitDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), exchange.getExchangeSpecification()
        .getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public LoyalbitBalance getLoyalbitBalance() throws IOException {
    return loyalbitAuthenticated.getBalance(
        exchange.getExchangeSpecification().getApiKey(),
        exchange.getNonceFactory(),
        signatureCreator
    );
  }
}
