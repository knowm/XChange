package org.knowm.xchange.gatecoin;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gatecoin.service.GatecoinAccountService;
import org.knowm.xchange.gatecoin.service.GatecoinMarketDataService;
import org.knowm.xchange.gatecoin.service.GatecoinTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Sumedha
 */
public class GatecoinExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
  }

  @Override
  protected void initServices() {

    this.marketDataService = new GatecoinMarketDataService(this);
    this.tradeService = new GatecoinTradeService(this);
    this.accountService = new GatecoinAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.gatecoin.com");
    exchangeSpecification.setHost("api.gatecoin.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Gatecoin");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

}
