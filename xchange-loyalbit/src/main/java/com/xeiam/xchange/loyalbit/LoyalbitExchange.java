package com.xeiam.xchange.loyalbit;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.loyalbit.service.polling.LoyalbitAccountService;
import com.xeiam.xchange.loyalbit.service.polling.LoyalbitMarketDataService;
import com.xeiam.xchange.loyalbit.service.polling.LoyalbitTradeService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Matija Mazi
 */
public class LoyalbitExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new LoyalbitMarketDataService(this);
    this.pollingTradeService = new LoyalbitTradeService(this);
    this.pollingAccountService = new LoyalbitAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.loyalbit.com");
    exchangeSpecification.setHost("www.loyalbit.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Loyalbit");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
