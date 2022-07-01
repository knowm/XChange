package org.knowm.xchange.coinegg.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinegg.CoinEggAuthenticated;
import org.knowm.xchange.coinegg.dto.trade.CoinEggTradeAdd;
import org.knowm.xchange.coinegg.dto.trade.CoinEggTradeCancel;
import org.knowm.xchange.coinegg.dto.trade.CoinEggTradeView;
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
    this.coinEggAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                CoinEggAuthenticated.class, exchange.getExchangeSpecification())
            .build();
  }

  // TODO: Sort Out Method Grammar
  public CoinEggTradeAdd getCoinEggTradeAdd(
      BigDecimal amount, BigDecimal price, String type, String coin) throws IOException {
    return coinEggAuthenticated.getTradeAdd(
        apiKey, signer, nonceFactory.createValue(), amount, price, type, coin);
  }

  public CoinEggTradeCancel getCoinEggTradeCancel(String id, String coin) throws IOException {
    return coinEggAuthenticated.getTradeCancel(
        apiKey, signer, nonceFactory.createValue(), id, coin);
  }

  public CoinEggTradeView getCoinEggTradeView(String tradeID, String coin) throws IOException {
    return coinEggAuthenticated.getTradeView(
        apiKey, signer, nonceFactory.createValue(), tradeID, coin);
  }
}
