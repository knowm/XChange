package org.knowm.xchange.therock;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.therock.service.TheRockAccountService;
import org.knowm.xchange.therock.service.TheRockMarketDataService;
import org.knowm.xchange.therock.service.TheRockTradeService;
import org.knowm.xchange.utils.nonce.TimestampIncrementingNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Matija Mazi
 */
public class TheRockExchange extends BaseExchange implements Exchange {
  /**
   * @deprecated use TheRockCancelOrderParams instead
   */
  public static final String CURRENCY_PAIR = "CURRENCY_PAIR";

  private SynchronizedValueFactory<Long> nonceFactory = new TimestampIncrementingNonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    super.applySpecification(exchangeSpecification);
  }

  @Override
  protected void initServices() {
    this.marketDataService = new TheRockMarketDataService(this);
    if (exchangeSpecification.getApiKey() != null && exchangeSpecification.getSecretKey() != null) {
      this.tradeService = new TheRockTradeService(this);
      this.accountService = new TheRockAccountService(this);
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
