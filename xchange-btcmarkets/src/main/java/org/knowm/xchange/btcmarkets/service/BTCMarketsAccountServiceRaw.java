package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticated;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BTCMarketsAccountServiceRaw extends BTCMarketsBaseService {

  private final BTCMarketsDigest signer;
  private final BTCMarketsAuthenticated btcm;
  private final SynchronizedValueFactory<Long> nonceFactory;

  protected BTCMarketsAccountServiceRaw(Exchange exchange) {
    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
    this.btcm = RestProxyFactory.createProxy(BTCMarketsAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signer = new BTCMarketsDigest(exchange.getExchangeSpecification().getSecretKey());
  }

  public List<BTCMarketsBalance> getBTCMarketsBalance() throws IOException {
    return btcm.getBalance(exchange.getExchangeSpecification().getApiKey(), nonceFactory, signer);
  }
}
