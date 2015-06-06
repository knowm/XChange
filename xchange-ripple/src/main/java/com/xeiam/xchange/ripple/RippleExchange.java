package com.xeiam.xchange.ripple;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.ripple.service.polling.RippleMarketDataService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

public class RippleExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public void applySpecification(final ExchangeSpecification exchangeSpecification) {
    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new RippleMarketDataService(this);

    // TODO implements trade and account service
    this.pollingTradeService = null;
    this.pollingAccountService = null;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    final ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.ripple.com/");
    exchangeSpecification.setExchangeName("Ripple");
    exchangeSpecification.setExchangeDescription("Ripple is a payment system, currency exchange and remittance network");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
