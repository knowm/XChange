package org.knowm.xchange.coinbene;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinbene.service.CoinbeneAccountService;
import org.knowm.xchange.coinbene.service.CoinbeneMarketDataService;
import org.knowm.xchange.coinbene.service.CoinbeneTradeService;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinbeneExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {

    this.marketDataService = new CoinbeneMarketDataService(this);
    this.accountService = new CoinbeneAccountService(this);
    this.tradeService = new CoinbeneTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.coinbene.com/");
    exchangeSpecification.setHost("coinbene.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Coinbene");
    exchangeSpecification.setExchangeDescription("Coinbene is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {}
}
