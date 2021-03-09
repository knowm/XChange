package org.knowm.xchange.coindcx;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coindcx.service.CoindcxMarketDataService;
import org.knowm.xchange.coindcx.service.CoindcxTradeService;
import org.knowm.xchange.utils.nonce.TimestampIncrementingNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoindcxExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new TimestampIncrementingNonceFactory();

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://public.coindcx.com");
    exchangeSpecification.setHost("public.coindcx.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Coindcx");
    exchangeSpecification.setExchangeDescription("Coindcx Exchange custom Exchange");
    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new CoindcxMarketDataService(this);
    this.tradeService = new CoindcxTradeService(this);
  }
}
