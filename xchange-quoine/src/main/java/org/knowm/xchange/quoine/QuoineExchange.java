package org.knowm.xchange.quoine;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.quoine.service.QuoineAccountService;
import org.knowm.xchange.quoine.service.QuoineMarketDataService;
import org.knowm.xchange.quoine.service.QuoineTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class QuoineExchange extends BaseExchange implements Exchange {

  public static final String KEY_TOKEN_ID = "KEY_TOKEN_ID";
  public static final String KEY_USER_SECRET = "KEY_USER_SECRET";

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {

    boolean useMargin = (Boolean) exchangeSpecification.getExchangeSpecificParametersItem("Use_Margin");

    this.marketDataService = new QuoineMarketDataService(this);
    this.accountService = new QuoineAccountService(this, useMargin);
    this.tradeService = new QuoineTradeService(this, useMargin);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.quoine.com");
    exchangeSpecification.setExchangeName("Quoine");
    exchangeSpecification.setExchangeSpecificParametersItem("Use_Margin", false);
    exchangeSpecification.setExchangeSpecificParametersItem("Leverage_Level", "1");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
