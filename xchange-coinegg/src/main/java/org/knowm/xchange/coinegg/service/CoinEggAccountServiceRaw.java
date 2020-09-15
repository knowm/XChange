package org.knowm.xchange.coinegg.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinegg.CoinEggAuthenticated;
import org.knowm.xchange.coinegg.dto.accounts.CoinEggBalance;
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
    this.coinEggAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                CoinEggAuthenticated.class, exchange.getExchangeSpecification())
            .build();
  }

  public CoinEggBalance getCoinEggBalance() throws IOException {
    return coinEggAuthenticated.getBalance(apiKey, nonceFactory.createValue(), signer);
  }
}
