package com.xeiam.xchange.cointrader.service.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cointrader.CointraderAuthenticated;
import com.xeiam.xchange.cointrader.dto.CointraderRequest;
import com.xeiam.xchange.cointrader.dto.account.CointraderBalance;
import com.xeiam.xchange.cointrader.service.CointraderDigest;

import si.mazi.rescu.RestProxyFactory;

public class CointraderAccountServiceRaw extends CointraderBasePollingService {

  private final CointraderDigest signatureCreator;
  private final CointraderAuthenticated cointraderAuthenticated;

  protected CointraderAccountServiceRaw(Exchange exchange) {
    super(exchange);
    this.cointraderAuthenticated = RestProxyFactory.createProxy(CointraderAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = new CointraderDigest(exchange.getExchangeSpecification().getSecretKey());
  }

  public Map<String, CointraderBalance> getCointraderBalance() throws IOException {
    return cointraderAuthenticated.getBalance(exchange.getExchangeSpecification().getApiKey(), signatureCreator, new CointraderRequest()).getData();
  }
}
