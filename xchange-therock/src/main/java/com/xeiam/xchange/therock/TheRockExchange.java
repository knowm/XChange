package com.xeiam.xchange.therock;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.therock.service.polling.TheRockAccountService;
import com.xeiam.xchange.therock.service.polling.TheRockMarketDataService;
import com.xeiam.xchange.therock.service.polling.TheRockTradeService;
import com.xeiam.xchange.utils.nonce.TimestampIncrementingNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Matija Mazi
 */
public class TheRockExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new TimestampIncrementingNonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new TheRockMarketDataService(this);
    if (exchangeSpecification.getApiKey() != null && exchangeSpecification.getSecretKey() != null) {
      this.pollingTradeService = new TheRockTradeService(this);
      this.pollingAccountService = new TheRockAccountService(this);
    }
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.therocktrading.com");
    exchangeSpecification.setHost("api.therocktrading.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("The Rock Trading");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
