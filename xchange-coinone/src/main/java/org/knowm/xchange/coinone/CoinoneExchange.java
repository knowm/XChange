package org.knowm.xchange.coinone;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinone.service.CoinoneAccountService;
import org.knowm.xchange.coinone.service.CoinoneMarketDataService;
import org.knowm.xchange.coinone.service.CoinoneTradeService;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinoneExchange extends BaseExchange implements Exchange {
  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  public static enum period {
    hour,
    day
  }

  @Override
  protected void initServices() {
    this.marketDataService = new CoinoneMarketDataService(this);
    this.accountService = new CoinoneAccountService(this);
    this.tradeService = new CoinoneTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.coinone.co.kr");
    exchangeSpecification.setHost("www.coinone.co.kr");
    exchangeSpecification.setExchangeName("Coinone");
    exchangeSpecification.setExchangeDescription("Coinone is a block chain exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {}
}
