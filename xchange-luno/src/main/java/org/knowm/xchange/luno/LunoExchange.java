package org.knowm.xchange.luno;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.luno.service.LunoAccountService;
import org.knowm.xchange.luno.service.LunoMarketDataService;
import org.knowm.xchange.luno.service.LunoTradeService;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Benedikt Bünz */
public class LunoExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {

    this.marketDataService = new LunoMarketDataService(this);
    this.tradeService = new LunoTradeService(this);
    this.accountService = new LunoAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.mybitx.com");
    exchangeSpecification.setHost("api.mybitx.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Luno");
    exchangeSpecification.setExchangeDescription("Luno is a bitcoin exchange.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new RuntimeException("Nonce parameter is not required by Luno API.");
  }

  @Override
  public void remoteInit() throws IOException {}
}
