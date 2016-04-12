package org.knowm.xchange.quoine;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.quoine.service.polling.QuoineAccountService;
import org.knowm.xchange.quoine.service.polling.QuoineMarketDataService;
import org.knowm.xchange.quoine.service.polling.QuoineTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class QuoineExchange extends BaseExchange implements Exchange {

  public static final String KEY_USER_ID = "KEY_USER_ID";
  public static final String KEY_USER_SECRET = "KEY_USER_SECRET";

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {

    boolean useMargin = (Boolean) exchangeSpecification.getExchangeSpecificParametersItem("Use_Margin");

    this.pollingMarketDataService = new QuoineMarketDataService(this);
    this.pollingAccountService = new QuoineAccountService(this, useMargin);
    this.pollingTradeService = new QuoineTradeService(this, useMargin);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.quoine.com");
    exchangeSpecification.setExchangeName("Quoine");
    exchangeSpecification.setExchangeSpecificParametersItem("Use_Margin", false);
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
