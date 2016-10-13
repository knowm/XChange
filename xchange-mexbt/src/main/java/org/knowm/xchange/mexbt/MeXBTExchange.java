package org.knowm.xchange.mexbt;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.mexbt.service.polling.MeXBTAccountService;
import org.knowm.xchange.mexbt.service.polling.MeXBTMarketDataService;
import org.knowm.xchange.mexbt.service.polling.MeXBTTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class MeXBTExchange extends BaseExchange implements Exchange {

  public static final String PRIVATE_API_URI_KEY = "private-api";

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {

    this.pollingMarketDataService = new MeXBTMarketDataService(this);
    if (exchangeSpecification.getApiKey() != null && exchangeSpecification.getUserName() != null) {
      this.pollingAccountService = new MeXBTAccountService(this);
      this.pollingTradeService = new MeXBTTradeService(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(getClass());
    spec.setExchangeName("mexbt");
    spec.setExchangeDescription("Mexican Bitcoin Exchange");
    spec.setSslUri("https://data.mexbt.com");
    spec.setExchangeSpecificParametersItem(PRIVATE_API_URI_KEY, "https://private-api.mexbt.com");
    return spec;
  }

}
