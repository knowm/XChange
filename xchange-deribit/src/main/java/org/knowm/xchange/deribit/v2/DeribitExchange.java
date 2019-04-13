package org.knowm.xchange.deribit.v2;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.deribit.v2.service.DeribitMarketDataService;
import org.knowm.xchange.utils.nonce.ExpirationTimeFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;

public class DeribitExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new ExpirationTimeFactory(30);

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
  }

  @Override
  protected void initServices() {
    this.marketDataService = new DeribitMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.deribit.com");
    exchangeSpecification.setHost("deribit.com");
    //    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Deribit");
    exchangeSpecification.setExchangeDescription("Deribit is a Bitcoin futures exchange");
    return exchangeSpecification;
  }

  public ExchangeSpecification getSandboxExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://test.deribit.com/");
    exchangeSpecification.setHost("test.deribit.com");
    //    exchangeSpecification.setPort(80);
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {
    //    updateExchangeMetaData();
  }
}
