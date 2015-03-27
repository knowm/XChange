package com.xeiam.xchange.taurus.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.taurus.TaurusAuthenticated;
import com.xeiam.xchange.taurus.dto.account.TaurusBalance;
import com.xeiam.xchange.taurus.service.TaurusDigest;

public class TaurusAccountServiceRaw extends TaurusBasePollingService {

  private final TaurusDigest signatureCreator;
  private final TaurusAuthenticated taurusAuthenticated;

  protected TaurusAccountServiceRaw(Exchange exchange) {
    super(exchange);

    this.taurusAuthenticated = RestProxyFactory.createProxy(TaurusAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = TaurusDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), exchange.getExchangeSpecification()
        .getUserName(), exchange.getExchangeSpecification().getApiKey());
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
    return taurusAuthenticated.getBitcoinDepositAddress(exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
  }
}
