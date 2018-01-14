package org.xchange.coinegg.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.xchange.coinegg.CoinEggAuthenticated;
import org.xchange.coinegg.dto.accounts.CoinEggBalance;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinEggAccountServiceRaw extends CoinEggBaseService {

  private CoinEggAuthenticated coinEggAuthenticated;
  
  private String apiKey;
  private String tradePassword;
  private CoinEggDigest signer;
  private SynchronizedValueFactory<Long> nonceFactory;
  
	public CoinEggAccountServiceRaw(Exchange exchange) {
		super(exchange);
		
		ExchangeSpecification spec = exchange.getExchangeSpecification();
		
		this.apiKey = spec.getApiKey();
		this.signer = CoinEggDigest.createInstance(spec.getSecretKey());
		this.tradePassword = spec.getPassword();
		this.nonceFactory = exchange.getNonceFactory();
		this.coinEggAuthenticated = RestProxyFactory.createProxy(CoinEggAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
	}
	
	public CoinEggBalance getCoinEggBalance() throws IOException {
	  return coinEggAuthenticated.getBalance(apiKey, nonceFactory.createValue(), signer);
	}
}
