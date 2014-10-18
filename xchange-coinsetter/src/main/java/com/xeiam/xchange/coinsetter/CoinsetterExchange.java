package com.xeiam.xchange.coinsetter;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.service.polling.CoinsetterMarketDataService;

/**
 * Coinsetter exchange.
 */
public class CoinsetterExchange extends BaseExchange implements Exchange {

  public static final String EXCHANGE_SMART = "SMART";
  public static final String EXCHANGE_COINSETTER = "COINSETTER";
  public static final String DEFAULT_EXCHANGE = EXCHANGE_SMART;

  public static final int DEFAULT_DEPTH = 10;

  public static final String DEPTH_FORMAT_PAIRED = "PAIRED";
  public static final String DEPTH_FORMAT_LIST = "LIST";
  public static final String DEFAULT_DEPTH_FORMAT = DEPTH_FORMAT_PAIRED;

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new CoinsetterMarketDataService(exchangeSpecification);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(getClass());
    exchangeSpecification.setSslUri("https://api.coinsetter.com/v1");
    exchangeSpecification.setHost("api.coinsetter.com");
    exchangeSpecification.setExchangeName("Coinsetter");
    return exchangeSpecification;
  }

}
