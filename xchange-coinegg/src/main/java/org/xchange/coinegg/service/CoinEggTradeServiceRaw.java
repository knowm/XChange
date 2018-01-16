package org.xchange.coinegg.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.xchange.coinegg.CoinEggAuthenticated;
import org.xchange.coinegg.dto.trade.CoinEggTradeAdd;
import org.xchange.coinegg.dto.trade.CoinEggTradeCancel;
import org.xchange.coinegg.dto.trade.CoinEggTradeView;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinEggTradeServiceRaw extends CoinEggBaseService {
 
  private CoinEggAuthenticated coinEggAuthenticated;
  
  private String apiKey;
  private String tradePassword;
  private CoinEggDigest signer;
  private SynchronizedValueFactory<Long> nonceFactory;
  
  public CoinEggTradeServiceRaw(Exchange exchange) {
    super(exchange);
      
    ExchangeSpecification spec = exchange.getExchangeSpecification();
      
    this.apiKey = spec.getApiKey();
    this.signer = CoinEggDigest.createInstance(spec.getSecretKey());
    this.tradePassword = spec.getPassword();
    this.nonceFactory = exchange.getNonceFactory();
    this.coinEggAuthenticated = RestProxyFactory.createProxy(CoinEggAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
  }
  
  // TODO: Sort Out Method Grammar
  public CoinEggTradeAdd getCoinEggTradeAdd(BigDecimal amount, BigDecimal price, String type, String coin) throws IOException {
    return coinEggAuthenticated.getTradeAdd(apiKey, signer, nonceFactory.createValue(), amount, price, type, coin);
  }
  
  public CoinEggTradeCancel getCoinEggTradeCancel(String id, String coin) throws IOException {
    return coinEggAuthenticated.getTradeCancel(apiKey, signer, nonceFactory.createValue(), id, coin);
  }
  
  public CoinEggTradeView getCoinEggTradeView(String tradeID, String coin) throws IOException {
    return coinEggAuthenticated.getTradeView(apiKey, signer, nonceFactory.createValue(), tradeID, coin);
  }

}
