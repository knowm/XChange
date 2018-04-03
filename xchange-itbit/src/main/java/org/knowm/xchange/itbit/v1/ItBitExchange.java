package org.knowm.xchange.itbit.v1;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.itbit.v1.service.ItBitAccountService;
import org.knowm.xchange.itbit.v1.service.ItBitMarketDataService;
import org.knowm.xchange.itbit.v1.service.ItBitTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class ItBitExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new ItBitMarketDataService(this);
    this.accountService = new ItBitAccountService(this);
    this.tradeService = new ItBitTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.itbit.com");
    exchangeSpecification.setHost("api.itbit.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("ItBit");
    exchangeSpecification.setExchangeDescription("ItBit Bitcoin Exchange");
    exchangeSpecification.setExchangeSpecificParametersItem("authHost", " https://api.itbit.com");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
