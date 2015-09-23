package com.xeiam.xchange.btcmarkets.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcmarkets.BTCMarketsAuthenticated;
import com.xeiam.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import com.xeiam.xchange.btcmarkets.service.BTCMarketsDigest;

public class BTCMarketsAccountServiceRaw extends BTCMarketsBasePollingService {

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
