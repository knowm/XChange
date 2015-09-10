package com.xeiam.xchange.quoine;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.quoine.service.polling.QuoineAccountService;
import com.xeiam.xchange.quoine.service.polling.QuoineMarketDataService;
import com.xeiam.xchange.quoine.service.polling.QuoineTradeService;

import si.mazi.rescu.SynchronizedValueFactory;

public class QuoineExchange extends BaseExchange implements Exchange {

  public static final String KEY_USER_ID = "KEY_USER_ID";
  public static final String KEY_DEVICE_NAME = "KEY_DEVICE_NAME";
  public static final String KEY_USER_TOKEN = "KEY_USER_TOKEN";

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

    // not used by this exchange
    return null;
  }
}
