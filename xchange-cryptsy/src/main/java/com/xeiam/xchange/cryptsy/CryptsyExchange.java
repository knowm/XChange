package com.xeiam.xchange.cryptsy;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyAccountService;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyMarketDataService;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyPublicMarketDataService;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyTradeService;

/**
 * @author ObsessiveOrange
 */
public class CryptsyExchange extends BaseExchange implements Exchange {

  private CryptsyPublicMarketDataService pollingPublicMarketDataService;

  /**
   * Default constructor for ExchangeFactory
   */
  public CryptsyExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new CryptsyMarketDataService(exchangeSpecification);
    this.pollingAccountService = new CryptsyAccountService(exchangeSpecification);
    this.pollingTradeService = new CryptsyTradeService(exchangeSpecification);

    this.pollingPublicMarketDataService = new CryptsyPublicMarketDataService();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.cryptsy.com");
    exchangeSpecification.setHost("api.cryptsy.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptsy");
    exchangeSpecification.setExchangeDescription("Cryptsy is an altcoin exchange");

    return exchangeSpecification;
  }

  public void applyPublicSpecification(ExchangeSpecification exchangeSpecification) {

    this.pollingPublicMarketDataService = new CryptsyPublicMarketDataService(exchangeSpecification);
  }

  public static ExchangeSpecification getDefaultPublicExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(CryptsyExchange.class.getCanonicalName());
    exchangeSpecification.setSslUri("http://pubapi.cryptsy.com");
    exchangeSpecification.setHost("pubapi.cryptsy.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptsy");
    exchangeSpecification.setExchangeDescription("Cryptsy is an altcoin exchange");

    return exchangeSpecification;
  }

  public CryptsyPublicMarketDataService getPublicPollingMarketDataService() {

    return pollingPublicMarketDataService;
  }
}
