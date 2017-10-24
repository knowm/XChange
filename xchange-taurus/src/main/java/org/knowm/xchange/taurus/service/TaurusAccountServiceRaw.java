package org.knowm.xchange.taurus.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.taurus.TaurusAuthenticated;
import org.knowm.xchange.taurus.dto.account.TaurusBalance;

import si.mazi.rescu.RestProxyFactory;

public class TaurusAccountServiceRaw extends TaurusBaseService {

  private final TaurusDigest signatureCreator;
  private final TaurusAuthenticated taurusAuthenticated;

  protected TaurusAccountServiceRaw(Exchange exchange) {
    super(exchange);

    this.taurusAuthenticated = RestProxyFactory.createProxy(TaurusAuthenticated.class, exchange.getExchangeSpecification().getSslUri(),
        getClientConfig());
    this.signatureCreator = TaurusDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public TaurusBalance getTaurusBalance() throws IOException {
    return taurusAuthenticated.getBalance(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
  }

  public String withdrawTaurusFunds(BigDecimal amount, final String address) throws IOException {
    final String response = taurusAuthenticated.withdrawBitcoin(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory(), amount, address);
    if (!"ok".equals(response)) {
      throw new ExchangeException("Withdrawing funds from Taurus failed: " + response);
    }
    return response;
  }

  public String getTaurusBitcoinDepositAddress() throws IOException {
    return taurusAuthenticated.getBitcoinDepositAddress(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
  }
}
